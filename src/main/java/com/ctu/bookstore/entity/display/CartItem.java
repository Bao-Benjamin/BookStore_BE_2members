//package com.ctu.bookstore.entity.display;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.experimental.FieldDefaults;
//
//import java.util.Set;
//
//
//@Entity
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Data
//public class CartItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    String id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id", nullable = false)
//    Cart cart;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="product_id")
//    Set<Product> products ;
//    int quatity;
//}
