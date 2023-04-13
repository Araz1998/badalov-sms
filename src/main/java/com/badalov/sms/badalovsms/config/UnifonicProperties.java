package com.badalov.sms.badalovsms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("sms.unifonic")
public class UnifonicProperties {

    private String url;

    private String appSid;

    private String senderId;

}
