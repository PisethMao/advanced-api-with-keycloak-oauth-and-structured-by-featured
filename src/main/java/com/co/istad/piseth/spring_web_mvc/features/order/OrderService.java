package com.co.istad.piseth.spring_web_mvc.features.order;

import com.co.istad.piseth.spring_web_mvc.features.order.dto.CreateOrderRequest;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse createNew(CreateOrderRequest createOrderRequest);
}
