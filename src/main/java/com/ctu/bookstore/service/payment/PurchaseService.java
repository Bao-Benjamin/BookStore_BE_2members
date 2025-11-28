package com.ctu.bookstore.service.payment;

import com.ctu.bookstore.dto.respone.payment.OrderItemResponse;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.payment.OrderItem;
import com.ctu.bookstore.repository.UserRepository;
import com.ctu.bookstore.repository.payment.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public List<OrderItemResponse> getPurchasedItemsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderItem> items = orderItemRepository.findAllByUserId(user.getId());

        // convert to DTO
        return items.stream().map(oi -> {
            OrderItemResponse dto = new OrderItemResponse();
            dto.setOrderId(oi.getOrder().getId().toString());
            dto.setProductId(oi.getProduct().getId().toString()); // lấy id từ product entity
            dto.setQuantity(oi.getQuantity());
            dto.setPriceAtTime(oi.getPriceAtTime());
            dto.setAbleComment(oi.isAbleComment());
            return dto;
        }).toList();
    }
}

