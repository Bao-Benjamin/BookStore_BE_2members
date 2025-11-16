package com.ctu.bookstore.controller.payment;

import com.ctu.bookstore.dto.request.payment.CheckoutRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.respone.payment.UserOrderResponse;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.payment.CheckoutRespone;
import com.ctu.bookstore.repository.UserRepository;
import com.ctu.bookstore.service.payment.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*; // 👈 Thêm annotation cần thiết

@RestController
@RequestMapping("/checkout")
public class PaymentController {

    // Sử dụng @RequiredArgsConstructor thay vì @Autowired cho constructor injection là cách tốt hơn
    @Autowired
    PaymentService paymentService;
    @Autowired
    UserRepository userRepository;


    @PostMapping("/create-session")
    public ApiRespone<CheckoutRespone> createCheckoutSession() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User không tồn tại trong payment controller"));
        try {
            CheckoutRespone response = paymentService.createCheckoutSession(user.getId());
            return ApiRespone.<CheckoutRespone>builder()
                    .result(response)
                    .build();
        } catch (StripeException e) {
            // Xử lý lỗi API Stripe
            throw new RuntimeException("Lỗi Stripe: " + e.getMessage());
        }
    }
    @PostMapping("/create-shipCOD")
    public ApiRespone<UserOrderResponse> createShipCOD() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User không tồn tại trong payment controller"));
        try {
            UserOrderResponse response = paymentService.BuyByShipCOD(user.getId());
            return ApiRespone.<UserOrderResponse>builder()
                    .result(response)
                    .build();
        } catch (RuntimeException e) {
            // Xử lý lỗi API Stripe
            throw new RuntimeException("Lỗi khi gọi api create-shipCOD");
        }
    }


//    @PostMapping("/create-session") // Ánh xạ tới POST /checkout/create-session
//    @ResponseStatus(HttpStatus.CREATED) // Thường dùng 201 Created khi tạo tài nguyên
//    public ApiRespone<CheckoutRespone> createCheckoutSession(@RequestBody CheckoutRequest checkoutRequest)
//            throws StripeException {
//
//        // 1. Lấy Username/Email từ Security Context (Đã đăng nhập)
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("Username từ Context: " + username);
//
//        // 2. Tìm User Entity từ Username
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(
//
//                        () -> new RuntimeException("User không tồn tại: " + username));
//
//        // 3. Gọi Service để tạo Stripe Session
//        CheckoutRespone checkoutResponse = paymentService.createCheckoutSession(user.getId(), checkoutRequest);
//
//        return ApiRespone.<CheckoutRespone>builder()
//                .result(checkoutResponse)
//                .build();
//    }
}