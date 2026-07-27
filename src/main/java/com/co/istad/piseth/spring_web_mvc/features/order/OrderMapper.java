package com.co.istad.piseth.spring_web_mvc.features.order;

import com.co.istad.piseth.spring_web_mvc.features.order.dto.CreateOrderRequest;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderLineDto;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderedBy", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Order mapCreateOrderRequestToOrder(
            CreateOrderRequest createOrderRequest
    );

    OrderResponse mapOrderToOrderResponse(Order order);

    @Mapping(target = "orderLineId", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "quantity", source = "qty")
    OrderLine mapOrderLineDtoToOrderLine(OrderLineDto orderLineDto);

    default List<OrderLineDto> mapOrderLineToOrderLineDto(
            List<OrderLine> orderLines
    ) {
        if (orderLines == null) {
            return Collections.emptyList();
        }
        return orderLines.stream()
                .map(orderLine -> OrderLineDto.builder()
                        .productCode(orderLine.getProduct().getCode())
                        .qty(orderLine.getQuantity())
                        .discount(orderLine.getDiscount())
                        .build())
                .toList();
    }
}