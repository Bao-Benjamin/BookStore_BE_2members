package com.ctu.bookstore.repository.display;

import com.ctu.bookstore.entity.display.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
}
