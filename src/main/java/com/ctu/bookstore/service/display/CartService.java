package com.ctu.bookstore.service.display;

import com.ctu.bookstore.dto.request.display.CartItemRequest;
import com.ctu.bookstore.dto.respone.display.CartResponse;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.display.Cart;
import com.ctu.bookstore.entity.display.CartItem;
import com.ctu.bookstore.entity.display.Product;
import com.ctu.bookstore.mapper.display.CartMapper;
import com.ctu.bookstore.repository.UserRepository;
import com.ctu.bookstore.repository.display.CartRepository;
import com.ctu.bookstore.repository.display.ProductRepository;
import com.ctu.bookstore.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserService userService;
    @Autowired
    CartMapper cartMapper;
    public Cart addOrUpdateCart(String userId, CartItemRequest cartItemRequest){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not exist to create cart"));
        Cart cart = cartRepository.findByUserId(userId).orElseGet(
                ()->Cart.builder()
                        .user(user)
//                            .createdAt(LocalDateTime.now())
//                            .updatedAt(LocalDateTime.now())
                        .cartItems(new HashSet<>())
                        .build()
        );
        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        if (product.getQuantity() < cartItemRequest.getQuantity()) {
            throw new RuntimeException("Số lượng tồn kho không đủ.");
        }
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item->item.getProduct().getId().equals(cartItemRequest.getProductId()))
                .findFirst()
                .orElse(null);
        if(existingItem!=null){
            existingItem.setQuatity(existingItem.getQuatity()+cartItemRequest.getQuantity());
        }else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .cart(cart)
                    .quatity(cartItemRequest.getQuantity())
                    .build();
            cart.getCartItems().add(newItem);
        }

        return cartRepository.save(cart) ;

    }

    public Cart getMyCart(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("khong có user trong Cart service"));
        return cartRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("không tìm thấy cart trong cart service"));


    }
    public int getSumMyCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("không có user trong Cart service"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("không tìm thấy cart trong cart service"));

        return cart.getCartItems().size();
    }


    public void delete(String id) {
        // Lấy cart từ DB
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cart với id: " + id));

        // Force load cartItems để tránh LazyInitializationException
        if (cart.getCartItems() != null) {
            cart.getCartItems().forEach(item -> item.setCart(cart)); // đảm bảo mỗi CartItem vẫn link Cart
            cart.getCartItems().clear(); // xóa tất cả CartItem khỏi Set, orphanRemoval sẽ xóa DB
        }

        // Xóa Cart
//        cartRepository.delete(cart);
    }
}
