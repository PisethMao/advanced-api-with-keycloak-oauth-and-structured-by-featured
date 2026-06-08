package com.co.istad.piseth.spring_web_mvc.features.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PatchProductRequest(
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,
        @DecimalMin(value = "0.01", inclusive = false, message = "Price must be greater than 0")
        @Positive
        BigDecimal price,
        @Min(value = 0, message = "Quantity must be greater than or equal to 0")
        @Positive
        Integer quantity,
        String description,
        Integer categoryId,
        Boolean isAvailable
) {
}
