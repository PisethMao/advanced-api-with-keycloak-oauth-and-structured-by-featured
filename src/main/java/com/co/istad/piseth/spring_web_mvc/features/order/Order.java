package com.co.istad.piseth.spring_web_mvc.features.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private String orderedBy;
    @Column(nullable = false)
    private boolean isDeleted;
    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;
}
