package com.co.istad.piseth.spring_web_mvc.features.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {
    boolean existsByProduct_Code(String productCode);
}
