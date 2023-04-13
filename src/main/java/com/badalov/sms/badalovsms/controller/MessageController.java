package com.badalov.sms.badalovsms.controller;

import com.badalov.sms.badalovsms.model.common.MessageRequest;
import com.badalov.sms.badalovsms.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MessageController implements MessageApi {

    private final MessageService messageService;

    public ResponseEntity<?> sendMessage(MessageRequest messageRequest) {
        return messageService.sendMessage(messageRequest);
    }
}
