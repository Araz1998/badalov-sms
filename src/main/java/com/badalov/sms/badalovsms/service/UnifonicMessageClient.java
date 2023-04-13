package com.badalov.sms.badalovsms.service;

import com.badalov.sms.badalovsms.config.ServiceProperties;
import com.badalov.sms.badalovsms.config.UnifonicProperties;
import com.badalov.sms.badalovsms.model.common.MessageRequest;
import com.badalov.sms.badalovsms.model.unifonic.UnifonicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnifonicMessageClient implements MessageClient {

    private final ServiceProperties serviceProperties;

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> sendMessage(MessageRequest messageRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UnifonicProperties unifonicByServiceId = getUnifonicPropertiesByServiceId(messageRequest.getServiceId());
        String unifonicUrl = unifonicByServiceId.getUrl();
        log.info("Sending request to Unifonic with url: {} ", unifonicUrl);
        return restTemplate.exchange(unifonicUrl.concat("?AppSid={AppSid}&SenderID={SenderID}&Body={Body}&Recipient={Recipient}"),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                UnifonicResponse.class,
                unifonicByServiceId.getAppSid(),
                unifonicByServiceId.getSenderId(),
                messageRequest.getMessage(),
                messageRequest.getRecipient());
    }

    private UnifonicProperties getUnifonicPropertiesByServiceId(String serviceId) {
        return Optional.ofNullable(serviceProperties.getSms().get(serviceId))
                .orElseThrow(() -> new NoSuchElementException("There's no such service with id:" + serviceId))
                .getUnifonic();
    }
}
