package com.ctu.bookstore.dto.request.display;

import lombok.*;

@Getter
@Setter

public class CartItemRequest {
    String productId;
    int quantity;
}
