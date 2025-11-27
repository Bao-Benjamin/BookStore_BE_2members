package com.ctu.bookstore.dto.respone.display;

import lombok.*;

@Getter
@Setter

public class CartItemResponse {
    String id;
    ProductResponse product; // Dùng lại ProductResponse
    int quatity;
    Double subtotal; // priceAtTime * quantity
}
