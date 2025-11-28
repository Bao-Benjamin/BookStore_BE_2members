package com.ctu.bookstore.entity.payment;

import com.ctu.bookstore.entity.display.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Getter //recommend using @Getter and @Setter cho entity(google for more). One reason is it easy to manage
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    UserOrder order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
    int quantity;
    double priceAtTime;
    @Column(name = "able_comment", nullable = false)
    private boolean ableComment = true;
}
