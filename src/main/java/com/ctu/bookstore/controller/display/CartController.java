package com.ctu.bookstore.controller.display;

import com.ctu.bookstore.dto.request.display.CartItemRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.respone.display.CartResponse;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.display.Cart;
import com.ctu.bookstore.entity.display.CartItem;
import com.ctu.bookstore.mapper.display.CartMapper;
import com.ctu.bookstore.repository.UserRepository;
import com.ctu.bookstore.repository.display.CartRepository;
import com.ctu.bookstore.service.display.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private CartMapper cartMapper;
//    private final String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    @PostMapping("/item")
    public ApiRespone<CartResponse> addOrUpdateItem(@RequestBody CartItemRequest request){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).orElseThrow();
        String userId = user.getId();
        System.out.println("userID trong carService: "+ userId);
        Cart cart = cartService.addOrUpdateCart(userId,request);
        return  ApiRespone.<CartResponse>builder()
                .result(cartMapper.toCartResponse(cart))
                .build();
    }
    @GetMapping("/my-cart")
    public ApiRespone<CartResponse> getMyCart(){
        return ApiRespone.<CartResponse>builder()
                .result(cartMapper.toCartResponse(cartService.getMyCart()))
                .build();
    }
    @DeleteMapping("/delete")
    public void deleteCart(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(()->new RuntimeException("khong có user trong cart controller"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("khong có user trong cart controller"));
        cartService.delete(cart.getId());
    }
    @GetMapping("/size")
    public ApiRespone<Integer> sizeOfCart(){
        return ApiRespone.<Integer>builder()
                .result(cartService.getSumMyCart())
                .build();
    }
}
