package com.co.istad.piseth.spring_web_mvc.features.order.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID uuid,
        Instant createdAt,
        String orderedBy,
        Boolean isDeleted,
        String remark,
        List<OrderLineDto> orderLines
) {
}
