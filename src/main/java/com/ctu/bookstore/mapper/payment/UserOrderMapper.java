package com.ctu.bookstore.mapper.payment;

import com.ctu.bookstore.dto.respone.payment.UserOrderResponse;
import com.ctu.bookstore.entity.payment.UserOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserOrderMapper {
    UserOrderResponse toUserOrderResponse(UserOrder userOrder);
}
