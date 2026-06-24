package com.co.istad.piseth.spring_web_mvc.features.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        String remark,
        @NotEmpty(message = "Order must have at least one item")
        List<@NotNull(message = "Item cannot be null") OrderLineDto> orderLines
) {
}
