package com.ctu.bookstore.dto.respone.payment;


import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private String orderId;
    private String productId;
    private int quantity;
    private double priceAtTime;
    private boolean ableComment;
}
