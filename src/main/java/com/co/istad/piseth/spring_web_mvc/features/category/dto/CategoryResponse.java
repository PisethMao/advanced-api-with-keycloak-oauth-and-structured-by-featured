package com.co.istad.piseth.spring_web_mvc.features.category.dto;

public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String image
) {
}
