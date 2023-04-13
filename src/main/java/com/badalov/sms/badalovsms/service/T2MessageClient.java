package com.badalov.sms.badalovsms.service;

import com.badalov.sms.badalovsms.builder.MessageBuilder;
import com.badalov.sms.badalovsms.config.ServiceProperties;
import com.badalov.sms.badalovsms.config.T2Properties;
import com.badalov.sms.badalovsms.model.common.MessageRequest;
import com.badalov.sms.badalovsms.model.t2.T2Request;
import com.badalov.sms.badalovsms.model.t2.T2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class T2MessageClient implements MessageClient {

    private final ServiceProperties serviceProperties;

    private final MessageBuilder messageBuilder;

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> sendMessage(MessageRequest messageRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        T2Request request = messageBuilder.buildT2Request(messageRequest);
        String t2Url = getT2PropertiesByServiceId(messageRequest.getServiceId()).getUrl();

        log.info("Sending request to T2 with url: {} and body: {}", t2Url, request);

        return restTemplate.postForEntity(t2Url, new HttpEntity<>(request, headers), T2Response.class);
    }

    private T2Properties getT2PropertiesByServiceId(String serviceId) {
        return Optional.ofNullable(serviceProperties.getSms().get(serviceId))
                .orElseThrow(() -> new NoSuchElementException("There's no such service with id:" + serviceId))
                .getT2();
    }
}
