package com.ctu.bookstore.dto.respone.payment;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    String orderId;
    String productId;
    int quantity;
    double priceAtTime;
    boolean ableComment;
}
