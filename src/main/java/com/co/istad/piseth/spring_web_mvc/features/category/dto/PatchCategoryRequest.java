package com.co.istad.piseth.spring_web_mvc.features.category.dto;

public record PatchCategoryRequest(
        String name,
        String description,
        String image
) {
}