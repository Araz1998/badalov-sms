package com.badalov.sms.badalovsms.service;

import com.badalov.sms.badalovsms.config.T2Properties;
import com.badalov.sms.badalovsms.config.UnifonicProperties;
import com.badalov.sms.badalovsms.model.common.ErrorResponse;
import com.badalov.sms.badalovsms.model.common.MessageRequest;
import com.badalov.sms.badalovsms.model.common.MessageResponse;
import com.badalov.sms.badalovsms.model.t2.T2Response;
import com.badalov.sms.badalovsms.model.unifonic.UnifonicResponse;
import com.badalov.sms.badalovsms.repository.SmsAuditLogRepository;
import com.badalov.sms.badalovsms.repository.model.SmsAuditLog;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.UUID;

import static com.badalov.sms.badalovsms.util.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final UnifonicProperties unifonicProperties;

    private final T2Properties t2Properties;

    private final SmsAuditLogRepository smsAuditLogRepository;

    private final MessageClient unifonicMessageClient;

    private final MessageClient t2MessageClient;


    @SneakyThrows
    public ResponseEntity<?> sendMessage(MessageRequest messageRequest) {
        log.info("MessageService.sendMessage method. Message request info: {}", messageRequest);

        String correlationId = UUID.randomUUID().toString();
        String recipient = messageRequest.getRecipient();
        ResponseEntity<UnifonicResponse> unifonicResponseEntity;
        try {
             unifonicResponseEntity = (ResponseEntity<UnifonicResponse>) unifonicMessageClient.sendMessage(messageRequest);
             log.info("Unifonic returned a response with status code: {} and body: {}", unifonicResponseEntity.getStatusCode(), unifonicResponseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            log.error("Failed to send message to Unifonic. Exception {}", ex.getMessage());
            UnifonicResponse unifonicResponse = ex.getResponseBodyAs(UnifonicResponse.class);
            return createUnifonicErrorResponse(unifonicResponse, correlationId, recipient);
        }

        UnifonicResponse unifonicResponse = unifonicResponseEntity.getBody();

        if (!unifonicResponseEntity.getStatusCode().is2xxSuccessful() || !unifonicResponse.isSuccess()) {
            SmsAuditLog smsAuditLog = SmsAuditLog.builder()
                    .unifonicMessageId(null)
                    .t2MessageId(null)
                    .status(ERROR_STATUS)
                    .errorCode(unifonicResponse.getErrorCode())
                    .errorMessage(unifonicResponse.getMessage())
                    .correlationId(correlationId)
                    .senderName(unifonicProperties.getSenderId())
                    .receiverPhoneNumber(recipient)
                    .timestamp(Instant.now())
                    .build();
            smsAuditLog = smsAuditLogRepository.save(smsAuditLog);
            log.info("Saved audit log for error response from unifonic: {}", smsAuditLog);

            ResponseEntity<T2Response> t2ResponseEntity = (ResponseEntity<T2Response>) t2MessageClient.sendMessage(messageRequest);
            T2Response t2Response = t2ResponseEntity.getBody();
            log.info("T2 returned a response with status code: {} and body: {}", t2ResponseEntity.getStatusCode(), t2Response);
            if (t2Response.getErrorCode() != T2_SUCCESS_CODE || !t2ResponseEntity.getStatusCode().is2xxSuccessful()) {
                return createT2ErrorResponse(t2Response, correlationId, recipient);
            } else {
                return createT2SuccessResponse(t2Response, correlationId, recipient);
            }
        } else {
            return createUnifonicSuccessResponse(unifonicResponse, correlationId, recipient);
        }
    }

    private ResponseEntity<MessageResponse> createT2ErrorResponse(T2Response t2Response,
                                                                  String correlationId,
                                                                  String recipient ) {
        SmsAuditLog smsAuditLog = SmsAuditLog.builder()
                .unifonicMessageId(null)
                .t2MessageId(null)
                .status(ERROR_STATUS)
                .errorCode(Integer.toString(t2Response.getErrorCode()))
                .errorMessage(T2_ERROR_MESSAGE_ID)
                .correlationId(correlationId)
                .senderName(t2Properties.getSender())
                .receiverPhoneNumber(recipient)
                .timestamp(Instant.now())
                .build();

        smsAuditLog = smsAuditLogRepository.save(smsAuditLog);
        log.info("Saved audit log for error response from T2: {}", smsAuditLog);

        MessageResponse messageResponse = MessageResponse.builder()
                .provider(T2_PROVIDER)
                .messageId(T2_ERROR_MESSAGE_ID)
                .status(Integer.toString(t2Response.getErrorCode()))
                .correlationId(correlationId)
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    private ResponseEntity<MessageResponse> createT2SuccessResponse(T2Response t2Response,
                                                                    String correlationId,
                                                                    String recipient) {
        SmsAuditLog smsAuditLog = SmsAuditLog.builder()
                .unifonicMessageId(null)
                .t2MessageId(t2Response.getSmsResponseData().getId())
                .status(SUCCESS_STATUS)
                .errorCode(Integer.toString(t2Response.getErrorCode()))
                .errorMessage(null)
                .correlationId(correlationId)
                .senderName(t2Properties.getSender())
                .receiverPhoneNumber(recipient)
                .timestamp(Instant.now())
                .build();

        smsAuditLog = smsAuditLogRepository.save(smsAuditLog);
        log.info("Saved audit log for success response from T2: {}", smsAuditLog);

        MessageResponse messageResponse = MessageResponse.builder()
                .provider(T2_PROVIDER)
                .messageId(t2Response.getSmsResponseData().getId())
                .status(SUCCESS_STATUS)
                .correlationId(correlationId)
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    private ResponseEntity<MessageResponse> createUnifonicSuccessResponse(UnifonicResponse unifonicResponse,
                                                                          String correlationId,
                                                                          String recipient) {
        SmsAuditLog smsAuditLog = SmsAuditLog.builder()
                .unifonicMessageId(unifonicResponse.getData().getMessageId())
                .t2MessageId(null)
                .status(SUCCESS_STATUS)
                .errorCode(unifonicResponse.getErrorCode())
                .errorMessage(null)
                .correlationId(correlationId)
                .senderName(t2Properties.getSender())
                .receiverPhoneNumber(recipient)
                .timestamp(Instant.now())
                .build();

        smsAuditLog = smsAuditLogRepository.save(smsAuditLog);
        log.info("Saved audit log for success response from Unifonic: {}", smsAuditLog);

        MessageResponse messageResponse = MessageResponse.builder()
                .provider(UNIFONIC_PROVIDER)
                .messageId(unifonicResponse.getData().getMessageId())
                .status(SUCCESS_STATUS)
                .correlationId(correlationId)
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    private ResponseEntity<ErrorResponse> createUnifonicErrorResponse(UnifonicResponse unifonicResponse,
                                                                      String correlationId,
                                                                      String recipient) {
        SmsAuditLog smsAuditLog = SmsAuditLog.builder()
                .unifonicMessageId(null)
                .t2MessageId(null)
                .status(ERROR_STATUS)
                .errorCode(unifonicResponse.getErrorCode())
                .errorMessage(unifonicResponse.getMessage())
                .correlationId(correlationId)
                .senderName(t2Properties.getSender())
                .receiverPhoneNumber(recipient)
                .timestamp(Instant.now())
                .build();

        smsAuditLog = smsAuditLogRepository.save(smsAuditLog);
        log.info("Saved audit log for error response from Unifonic: {}", smsAuditLog);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(unifonicResponse.getErrorCode())
                .errorMessage(unifonicResponse.getMessage())
                .status(unifonicResponse.getStatus())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
