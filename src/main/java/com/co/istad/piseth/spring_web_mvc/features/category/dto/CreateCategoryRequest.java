package com.co.istad.piseth.spring_web_mvc.features.category.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
        @Column(nullable = false)
        @NotEmpty
        @NotNull
        @NotBlank(message = "Name cannot be blank!!!")
        String name,
        String description,
        String image
) {
}
