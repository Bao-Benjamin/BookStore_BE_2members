package com.ctu.bookstore.dto.respone.display;

import lombok.*;

import java.util.List;
import java.util.Set;
@Getter
@Setter

public class CartResponse {
    String id;
    String userId;
    Set<CartItemResponse> cartItems;
    Double totalAmount;
}
