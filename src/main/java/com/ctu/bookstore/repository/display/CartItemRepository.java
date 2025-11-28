package com.ctu.bookstore.repository.display;

import com.ctu.bookstore.entity.display.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,String> {

}
