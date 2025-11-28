package com.ctu.bookstore.dto.request.payment;

import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.display.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutItemRequest {
    String productId;
    int quantity;
    String paymentMethod;
}
