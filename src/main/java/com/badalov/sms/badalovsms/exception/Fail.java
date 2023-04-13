package com.badalov.sms.badalovsms.exception;

import com.badalov.sms.badalovsms.model.ErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.badalov.sms.badalovsms.exception.FailType.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class Fail extends RuntimeException {
    final FailType type;
    final List<ErrorMessage> messages;
    final Throwable cause;

    public static Fail sql(String description, Throwable t) {
        return new Fail(SQL, List.of((new ErrorMessage(description, SQL.getKey()))), t);
    }

    public static Fail internal(String description, Throwable t) {
        return new Fail(INTERNAL, List.of(new ErrorMessage(description, INTERNAL.getKey())), t);
    }

    public static Fail inputs(List<ErrorMessage> messages, Throwable cause) {
        return new Fail(INPUT_GENERIC, messages, cause);
    }

    public static Fail notFound(String description) {
        return new Fail(RESOURCE_NOT_FOUND, List.of(new ErrorMessage(description, RESOURCE_NOT_FOUND.getKey())), null);
    }
}
