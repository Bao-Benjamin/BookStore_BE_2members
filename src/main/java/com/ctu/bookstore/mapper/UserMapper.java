package com.ctu.bookstore.mapper;

import com.ctu.bookstore.dto.request.UserRequest;
import com.ctu.bookstore.dto.respone.UserRespone;
import com.ctu.bookstore.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);
    UserRespone toUserRespone(User user);
}
