package com.badalov.sms.badalovsms.controller;

import com.badalov.sms.badalovsms.model.common.MessageRequest;
import com.badalov.sms.badalovsms.model.common.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("sms/gateway/message")
@Tag(name = "Message", description = "Send message API")
public interface MessageApi {

    @PostMapping("/send")
    @Operation(summary = "Send sms message to the operator")
    @ApiResponse(description = "OK", responseCode = "200", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    ResponseEntity<?> sendMessage(@Valid @RequestBody MessageRequest messageRequest);
}
