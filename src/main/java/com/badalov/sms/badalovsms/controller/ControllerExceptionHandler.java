package com.badalov.sms.badalovsms.controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.badalov.sms.badalovsms.exception.Fail;
import com.badalov.sms.badalovsms.model.ErrorMessage;
import com.badalov.sms.badalovsms.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(SQLServerException.class)
    public ResponseEntity<ErrorResponse> handleSqlServerException(SQLServerException ex) {
        log.error("ControllerExceptionHandler.handleSqlServerException: errorCause:{}, errorMessage:{}",
                ex.getCause(), ex.getMessage());
        return handleFail(Fail.sql("Data integrity exception", ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("ControllerExceptionHandler.handleMethodArgumentNotValidException: errorCause:{}, errorMessage:{}",
                ex.getCause(), ex.getMessage());
        List<ErrorMessage> errorMessages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(this::methodArgumentNotValidExceptionToErrorMassage)
                .toList();
        return handleFail(Fail.inputs(errorMessages, ex));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("ControllerExceptionHandler.handleNoSuchElementException: errorCause:{}, errorMessage:{}",
                ex.getCause(), ex.getMessage());
        return handleFail(Fail.notFound(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("ControllerExceptionHandler.handleException: errorCause:{}, errorMessage:{}",
                ex.getCause(), ex.getMessage());
        return handleFail(Fail.internal(ex.getMessage(), ex));
    }

    @ExceptionHandler(Fail.class)
    public ResponseEntity<ErrorResponse> handleFail(Fail fail) {
        log.error("ControllerExceptionHandler.handleFail: errorCause:{}, errorMessage:{}",
                fail.getCause(), fail.getMessage());
        return ResponseEntity
                .status(fail.getType().getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(fail.getType().getType(), fail.getMessages()));
    }

    private ErrorMessage methodArgumentNotValidExceptionToErrorMassage(ObjectError objectError) {
        if (objectError instanceof FieldError fieldError) {
            return new ErrorMessage(fieldError.getDefaultMessage(),
                    "fail.input." + fieldError.getField() + ".invalid");
        }
        return new ErrorMessage(objectError.getDefaultMessage(), "fail.input.invalid");
    }
}
