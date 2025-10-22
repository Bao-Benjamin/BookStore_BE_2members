//package com.ctu.bookstore.entity.display;
//
//import com.ctu.bookstore.entity.User;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.experimental.FieldDefaults;
//
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Data
//public class Cart {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String id;
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    User user;
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    Set<CartItem> cartItems ;
//
//
//}
