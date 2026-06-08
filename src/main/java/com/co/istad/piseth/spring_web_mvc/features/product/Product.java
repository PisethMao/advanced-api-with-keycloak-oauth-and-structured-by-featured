package com.co.istad.piseth.spring_web_mvc.features.product;

import com.co.istad.piseth.spring_web_mvc.features.category.Category;
import com.co.istad.piseth.spring_web_mvc.features.order.OrderLine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String code;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private boolean isAvailable;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;
}
