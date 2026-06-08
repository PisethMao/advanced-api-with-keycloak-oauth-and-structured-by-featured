package com.co.istad.piseth.spring_web_mvc.features.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(String code,
                              String name,
                              BigDecimal price,
                              Integer quantity,
                              String description,
                              boolean isAvailable,
                              String categoryName) {
}
