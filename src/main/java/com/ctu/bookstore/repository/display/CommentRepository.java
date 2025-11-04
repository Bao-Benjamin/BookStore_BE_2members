package com.ctu.bookstore.repository.display;

import com.ctu.bookstore.entity.display.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByProductIdOrderByCreatedAtDesc(String productId);
}
