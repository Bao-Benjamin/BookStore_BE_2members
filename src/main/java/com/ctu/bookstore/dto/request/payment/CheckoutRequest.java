package com.ctu.bookstore.dto.request.payment;

import com.ctu.bookstore.dto.request.display.CartItemRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {
    // List này chứa các sản phẩm mà người dùng muốn thanh toán.
    // Nếu list này RỖNG hoặc NULL, ta sẽ mặc định lấy TẤT CẢ sản phẩm từ GIỎ HÀNG (Cart) của User.
    List<CheckoutItemRequest> itemsToCheckout;
    private boolean isCartCheckout = false;
    // Bạn có thể thêm các trường khác như:
    // String shippingAddressId;
    // String couponCode;
}
