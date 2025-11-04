package com.ctu.bookstore.repository.display;

import com.ctu.bookstore.entity.display.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    @EntityGraph(attributePaths = {"imagesUrl", "comments"})
    Optional<Product> findById(String id);
}
