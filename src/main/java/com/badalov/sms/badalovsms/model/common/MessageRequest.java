package com.badalov.sms.badalovsms.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    @NotEmpty(message = "The serviceId is required.")
    @JsonProperty("serviceId")
    private String serviceId;

    @NotEmpty(message = "The message is required.")
    @JsonProperty("message")
    private String message;

    @NotEmpty(message = "The recipient is required.")
    @Pattern(regexp = "(\\+\\d{0,14})", message = "The recipient is not valid")
    @JsonProperty("recipient")
    private String recipient;

}
