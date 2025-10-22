package com.ctu.bookstore.dto.respone.display;

public class CartItemResponse {
    Long id;
    ProductResponse product; // Dùng lại ProductResponse
    int quantity;
    Double subtotal; // priceAtTime * quantity
}
