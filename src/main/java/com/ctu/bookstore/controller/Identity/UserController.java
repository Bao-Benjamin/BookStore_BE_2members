package com.ctu.bookstore.controller.Identity;

import com.ctu.bookstore.dto.request.UserUpdateRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.request.UserRequest;
import com.ctu.bookstore.dto.respone.UserRespone;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.payment.InforCheckout;
import com.ctu.bookstore.exception.ErrorCode;
import com.ctu.bookstore.mapper.UserMapper;
import com.ctu.bookstore.repository.UserRepository;
import com.ctu.bookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
//    @PostMapping
//    public ApiRespone<UserRespone> createUser(@RequestBody @Valid UserRequest userRequest){
//
//         return ApiRespone.<UserRespone>builder()
//                 .result(userMapper.toUserRespone(userService.createUser(userRequest)))
//                 .build();
//    }
    @PostMapping
    public ApiRespone<UserRespone> createUser(@RequestBody @Valid UserRequest userRequest){
        ApiRespone<User> apiRespone = new ApiRespone<>();


        return ApiRespone.<UserRespone>builder()
                .result(userService.createUser(userRequest))
                .build();
    }
    @GetMapping
    ApiRespone<List<UserRespone>> getUsers(){
        return ApiRespone.<List<UserRespone>>builder()
                .result(userService.getUsers())
                .build();
    }
    @GetMapping("/{userId}")
    ApiRespone<UserRespone> getUser(@PathVariable("userId") String userId){
        return ApiRespone.<UserRespone>builder()
                .result(userService.getUser(userId))
                .build();
    }
    @GetMapping("/info")
    ApiRespone<UserRespone> getInfor(){
        return ApiRespone.<UserRespone>builder()
                .result(userService.getMyInfor())
                .build();
    }
    @GetMapping("/info-checkout")
    ApiRespone<InforCheckout> getInforCheckout(){
        return ApiRespone.<InforCheckout>builder()
                .result(userService.getInforCheckout())
                .build();
    }
    @PutMapping("/{userId}")
    public ApiRespone<UserRespone> updateUser(@RequestBody UserUpdateRequest userRequestBody ){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(()-> new RuntimeException("Khong tim thay user trong user controller"));
        return ApiRespone.<UserRespone>builder()
                .result(userService.updateUser(user.getId(),userRequestBody))
                .build();
    }
}
