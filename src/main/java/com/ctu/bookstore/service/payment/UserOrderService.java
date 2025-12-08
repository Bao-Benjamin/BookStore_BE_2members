package com.ctu.bookstore.service.payment;

import com.ctu.bookstore.dto.respone.payment.UserOrderResponse;
import com.ctu.bookstore.entity.payment.UserOrder;
import com.ctu.bookstore.mapper.payment.UserOrderMapper;
import com.ctu.bookstore.repository.payment.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserOrderRepository userOrderRepository;
    private final UserOrderMapper userOrderMapper;

    public List<UserOrderResponse> getOrdersByUserId(String userId) {
        return userOrderRepository
                .findByUserIdOrderByOrderDateDesc(userId)
                .stream()
                .map(userOrderMapper::toUserOrderResponse)
                .toList();
    }
}
