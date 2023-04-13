package com.badalov.sms.badalovsms.model.unifonic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnifonicResponse {

    @JsonProperty("success")
    private boolean isSuccess;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("data")
    private UnifonicResponseData data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnifonicResponseData {

        @JsonProperty("MessageID")
        private String messageId;

        @JsonProperty("CorrelationID")
        private String correlationiD;

        @JsonProperty("Status")
        private String status;

        @JsonProperty("NumberOfUnits")
        private Integer numberOfUnits;

        @JsonProperty("Cost")
        private Integer cost;

        @JsonProperty("Balance")
        private Integer balance;

        @JsonProperty("Recipient")
        private String recipient;

        @JsonProperty("TimeCreated")
        private String timeCreated;

        @JsonProperty("CurrencyCode")
        private String currencyCode;

    }

}
