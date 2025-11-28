package com.ctu.bookstore.dto.request.display;

import com.ctu.bookstore.entity.display.CartItem;

import java.util.Set;

public class CartRequest {
    String userID;
    Set<CartItem> cartItems;
}
