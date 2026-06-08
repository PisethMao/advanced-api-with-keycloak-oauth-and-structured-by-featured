package com.co.istad.piseth.spring_web_mvc.features.category.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(
        @NotBlank(message = "Name is required")
        String name,
        String description,
        String image
) {
}