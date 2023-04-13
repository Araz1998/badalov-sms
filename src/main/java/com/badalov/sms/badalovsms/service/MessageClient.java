package com.badalov.sms.badalovsms.service;

import com.badalov.sms.badalovsms.model.common.MessageRequest;
import org.springframework.http.ResponseEntity;

public interface MessageClient {

    ResponseEntity<?> sendMessage(MessageRequest messageRequest);
}
