package com.ctu.bookstore.dto.request.payment;

import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.display.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CheckoutItemRequest {
    private String productId;
    private int quantity;
    String paymentMethod;
}
