package com.co.istad.piseth.spring_web_mvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AppGlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        List<FieldResponse> fieldResponses = new ArrayList<>();
        e.getFieldErrors().forEach(fieldError -> fieldResponses.add(new FieldResponse(fieldError.getField(), fieldError.getDefaultMessage())));
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Request validation failed")
                .timestamp(Instant.now())
                .errors(fieldResponses)
                .build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleException(ResponseStatusException e) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .status(e.getStatusCode().toString())
                .message("Server error:")
                .code(e.getStatusCode().value())
                .errors(e.getReason() != null ? List.of(e.getReason()) : List.of())
                .timestamp(Instant.now()).build();
        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }
}
