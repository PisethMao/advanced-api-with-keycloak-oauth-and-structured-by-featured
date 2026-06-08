package com.co.istad.piseth.spring_web_mvc.features.order;

import com.co.istad.piseth.spring_web_mvc.features.order.dto.CreateOrderRequest;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderLineDto;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order mapCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);
    OrderResponse mapOrderToOrderResponse(Order order);
    default List<OrderLineDto> mapOrderLineToOrderLineDto(List<OrderLine> orderLine){
        return orderLine.stream()
                .map(orderLines -> OrderLineDto.builder()
                        .productCode(orderLines.getProduct().getCode())
                        .qty(orderLines.getQuantity())
                        .discount(orderLines.getDiscount())
                        .build())
                .collect(Collectors.toList());
    }
}
