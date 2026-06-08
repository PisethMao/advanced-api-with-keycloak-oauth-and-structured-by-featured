package com.co.istad.piseth.spring_web_mvc.features.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Price cannot be null")
        BigDecimal price,
        @NotNull(message = "Quantity cannot be null")
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity,
        @NotNull(message = "Description cannot be null")
        String description,
        @NotNull(message = "Category ID cannot be null")
        Integer categoryId) {
}
