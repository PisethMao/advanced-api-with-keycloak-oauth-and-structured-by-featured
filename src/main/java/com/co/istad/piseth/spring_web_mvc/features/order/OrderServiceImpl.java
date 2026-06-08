package com.co.istad.piseth.spring_web_mvc.features.order;

import com.co.istad.piseth.spring_web_mvc.features.order.dto.CreateOrderRequest;
import com.co.istad.piseth.spring_web_mvc.features.order.dto.OrderResponse;
import com.co.istad.piseth.spring_web_mvc.features.product.Product;
import com.co.istad.piseth.spring_web_mvc.features.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {
        List<OrderLine> validOrderLine = new ArrayList<>();
        final Order order = orderMapper.mapCreateOrderRequestToOrder(createOrderRequest);
        boolean isValid = createOrderRequest.orderLines().stream()
                .allMatch(orderLineDto -> {
                    boolean isExisting = productRepository.existsById(orderLineDto.productCode());
                    if (isExisting) {
                        Product product = productRepository.findById(orderLineDto.productCode())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(product);
                        orderLine.setQuantity(orderLineDto.qty());
                        orderLine.setDiscount(orderLineDto.discount());
                        orderLine.setOrder(order);
                        validOrderLine.add(orderLine);
                    }
                    return isExisting;
                });
        if(!isValid) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        order.setCreatedAt(Instant.now());
        order.setIsDeleted(false);
        order.setOrderedBy("ADMIN");
        order.setOrderLines(validOrderLine);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapOrderToOrderResponse(savedOrder);
    }
}
