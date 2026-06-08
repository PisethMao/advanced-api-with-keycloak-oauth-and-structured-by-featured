package com.co.istad.piseth.spring_web_mvc.features.order;

import com.co.istad.piseth.spring_web_mvc.features.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_lines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderLineId;
    @ManyToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_uuid")
    private Order order;
    private Integer quantity;
    private Float discount;
}
