package com.badalov.sms.badalovsms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("sms.t2")
public class T2Properties {

    private String password;

    private String sender;

    private String username;

    private String url;
}
