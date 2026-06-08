package com.co.istad.piseth.spring_web_mvc.exception;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorResponse(
        String status,
        String message,
        Integer code,
        Instant timestamp,
        Object errors
) {
}
