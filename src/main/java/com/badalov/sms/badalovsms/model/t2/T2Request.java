package com.badalov.sms.badalovsms.model.t2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T2Request {

    @JsonProperty("message")
    private String message;

    @JsonProperty("number")
    private String number;

    @JsonProperty("password")
    private String password;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("username")
    private String username;

}
