package com.co.istad.piseth.spring_web_mvc.features.order;

import com.co.istad.piseth.spring_web_mvc.features.order.dto.CreateOrderRequest;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderResponse;
import org.springframework.security.oauth2.jwt.Jwt;

public interface OrderService {
    OrderResponse createNew(CreateOrderRequest createOrderRequest, Jwt jwt);
}
