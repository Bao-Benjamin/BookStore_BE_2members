package com.ctu.bookstore.dto.respone.payment;

import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.payment.OrderItem;
import com.ctu.bookstore.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderResponse {

    User user;

    Set<OrderItem> orderItems = new HashSet<>();
    String stripeSessionId;
    Double totalAmount;

    OrderStatus status;
    String shippingAddress;

    LocalDateTime orderDate;
    String paymentMethod;
}
