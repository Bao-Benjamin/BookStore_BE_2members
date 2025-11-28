package com.ctu.bookstore.controller.payment;

import com.ctu.bookstore.dto.respone.payment.OrderItemResponse;
import com.ctu.bookstore.service.payment.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/items")
    public ResponseEntity<List<OrderItemResponse>> getPurchasedItems() {
        List<OrderItemResponse> items = purchaseService.getPurchasedItemsForCurrentUser();
        return ResponseEntity.ok(items);
    }
}
