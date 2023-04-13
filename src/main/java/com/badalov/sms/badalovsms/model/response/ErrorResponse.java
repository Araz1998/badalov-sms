package com.badalov.sms.badalovsms.model.response;

import com.badalov.sms.badalovsms.model.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    String type;

    List<ErrorMessage> messages;
}
