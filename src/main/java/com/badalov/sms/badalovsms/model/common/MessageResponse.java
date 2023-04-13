package com.badalov.sms.badalovsms.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("messageId")
    private String messageId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("correlationId")
    private String correlationId;

}
