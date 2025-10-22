package com.ctu.bookstore.mapper;

import com.ctu.bookstore.dto.request.UserRequest;
import com.ctu.bookstore.dto.request.UserUpdateRequest;
import com.ctu.bookstore.dto.respone.UserRespone;
import com.ctu.bookstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest userRequest);

    UserRespone toUserRespone(User user);
    @Mapping(target = "roles" , ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
