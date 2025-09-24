package com.ctu.bookstore.controller;

import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.request.UserRequest;
import com.ctu.bookstore.dto.respone.UserRespone;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.exception.ErrorCode;
import com.ctu.bookstore.mapper.UserMapper;
import com.ctu.bookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
//    @PostMapping
//    public ApiRespone<UserRespone> createUser(@RequestBody @Valid UserRequest userRequest){
//
//         return ApiRespone.<UserRespone>builder()
//                 .result(userMapper.toUserRespone(userService.createUser(userRequest)))
//                 .build();
//    }
    @PostMapping
    public ApiRespone<User> createUser(@RequestBody @Valid UserRequest userRequest){
        ApiRespone<User> apiRespone = new ApiRespone<>();

        return apiRespone.<User>builder()
                .result(userService.createUser(userRequest))
                .build();

    }
}
