package com.ctu.bookstore.dto.respone.display;

import java.util.List;

public class CartResponse {
    Long id;
    String userId;
    List<CartItemResponse> cartItems;
    Double totalAmount;
}
