package com.co.istad.piseth.spring_web_mvc.features.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,
        @Positive
        @NotNull
        BigDecimal price,
        @Positive
        @NotNull
        Integer quantity,
        String description,
        @Positive
        @NotNull
        Integer categoryId) {
}
