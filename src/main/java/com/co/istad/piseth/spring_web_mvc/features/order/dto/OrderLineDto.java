package com.co.istad.piseth.spring_web_mvc.features.order.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record OrderLineDto(
        @NotBlank(message = "Product cannot be blank")
        @Size(min = 1, max = 255)
        String productCode,
        @Positive
        Integer qty,
        @Min(0)
        @Max(100)
        Float discount
) {
}
