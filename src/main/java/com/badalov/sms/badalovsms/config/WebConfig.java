package com.badalov.sms.badalovsms.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.badalov.sms.badalovsms.util.LoggingRequestInterceptor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .failOnUnknownProperties(false);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        if (LoggingRequestInterceptor.getLog().isDebugEnabled()) {
            ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
            builder = builder.requestFactory(() -> factory);
        }

        RestTemplate restTemplate = builder.build();
        restTemplate.setInterceptors(List.of(new LoggingRequestInterceptor()));
        return restTemplate;
    }
}
