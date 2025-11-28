package com.ctu.bookstore.entity.payment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CheckoutRespone {
    String stripeCheckoutUrl;
}
