package com.badalov.sms.badalovsms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FailType {
    SQL("fail.server", "fail.server.sql", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL("fail.internal", "fail.internal.generic", HttpStatus.INTERNAL_SERVER_ERROR),
    INPUT_GENERIC("fail.input", "fail.input.generic", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("fail.resource", "fail.resource.notfound", HttpStatus.NOT_FOUND);

    private final String type;
    private final String key;
    private final HttpStatus status;
}
