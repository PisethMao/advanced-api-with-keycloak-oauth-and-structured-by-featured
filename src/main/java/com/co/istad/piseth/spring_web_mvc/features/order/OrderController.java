package com.co.istad.piseth.spring_web_mvc.features.order;

import com.co.istad.piseth.spring_web_mvc.features.order.dto.CreateOrderRequest;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createNew(
            @Valid @RequestBody CreateOrderRequest createOrderRequest,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return orderService.createNew(createOrderRequest, jwt);
    }
}
