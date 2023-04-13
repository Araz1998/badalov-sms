package com.badalov.sms.badalovsms.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.badalov.sms.badalovsms.BaseTest;
import com.badalov.sms.badalovsms.model.common.MessageRequest;
import com.badalov.sms.badalovsms.model.common.MessageResponse;
import com.badalov.sms.badalovsms.util.TestConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.badalov.sms.badalovsms.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageServiceTest extends BaseTest {

    static WireMockServer wireMockServer;

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(26001);
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testSendMessageToUnifonic_shouldReturnSuccessfulResponse() {
        MessageRequest request = new MessageRequest();
        request.setServiceId(SERVICE_ID);
        request.setMessage(SUCCESSFUL_MESSAGE);
        request.setRecipient(UNIFONIC_RECIPIENT);

        HttpEntity<MessageRequest> entity = new HttpEntity<>(request);

        ResponseEntity<MessageResponse> responseEntity = restTemplate
                .exchange(TestConstants.SEND_MESSAGE_API, HttpMethod.POST, entity, MessageResponse.class);

        assertNotNull(responseEntity.getBody());
        assertEquals(UNIFONIC_PROVIDER, responseEntity.getBody().getProvider());
        assertEquals(SUCCESSFUL_STATUS, responseEntity.getBody().getStatus());
    }

    @Test
    public void testSendMessageToT2_ShouldReturnSuccessfulResponse() {
        MessageRequest request = new MessageRequest();
        request.setServiceId(SERVICE_ID);
        request.setMessage(T2_SUCCESSFUL_MESSAGE);
        request.setRecipient(RECIPIENT);

        HttpEntity<MessageRequest> entity = new HttpEntity<>(request);

        ResponseEntity<MessageResponse> responseEntity = restTemplate
                .exchange(TestConstants.SEND_MESSAGE_API, HttpMethod.POST, entity, MessageResponse.class);

        assertEquals(T2_PROVIDER, responseEntity.getBody().getProvider());
        assertEquals(SUCCESSFUL_STATUS, responseEntity.getBody().getStatus());
    }


    @Test
    public void testSendMessageToT2_ShouldReturnErrorResponse() {
        MessageRequest request = new MessageRequest();
        request.setServiceId(SERVICE_ID);
        request.setMessage(T2_ERROR_MESSAGE);
        request.setRecipient(RECIPIENT);

        HttpEntity<MessageRequest> entity = new HttpEntity<>(request);

        ResponseEntity<MessageResponse> responseEntity = restTemplate
                .exchange(TestConstants.SEND_MESSAGE_API, HttpMethod.POST, entity, MessageResponse.class);

        assertEquals(T2_PROVIDER, responseEntity.getBody().getProvider());
        assertEquals(ERROR_STATUS, responseEntity.getBody().getStatus());
        assertEquals(T2_MESSAGE_ID, responseEntity.getBody().getMessageId());
    }
}