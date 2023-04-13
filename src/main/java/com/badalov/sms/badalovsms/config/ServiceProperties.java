package com.badalov.sms.badalovsms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties()
public class ServiceProperties {
    private Map<String, Service> sms;

    @Data
    public static class Service {
        private UnifonicProperties unifonic;

        private T2Properties t2;
    }
}