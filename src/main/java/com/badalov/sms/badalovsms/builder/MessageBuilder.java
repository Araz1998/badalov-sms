package com.badalov.sms.badalovsms.builder;

import com.badalov.sms.badalovsms.config.T2Properties;
import com.badalov.sms.badalovsms.model.common.MessageRequest;
import com.badalov.sms.badalovsms.model.t2.T2Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageBuilder {

    private final T2Properties t2Properties;

    public T2Request buildT2Request(MessageRequest messageRequest) {
        T2Request request = new T2Request();
        request.setMessage(messageRequest.getMessage());
        request.setNumber(formatPhoneNumber(messageRequest.getRecipient()));
        request.setPassword(t2Properties.getPassword());
        request.setSender(t2Properties.getSender());
        request.setUsername(t2Properties.getUsername());
        return request;
    }

    private String formatPhoneNumber(String input) {
        return input.replace("+", "0");
    }

}
