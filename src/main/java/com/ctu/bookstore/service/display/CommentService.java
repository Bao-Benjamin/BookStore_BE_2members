package com.ctu.bookstore.service.display;

import com.ctu.bookstore.dto.request.display.CommentRequest;
import com.ctu.bookstore.dto.respone.display.CommentResponse;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.display.Comment;
import com.ctu.bookstore.entity.display.Product;
import com.ctu.bookstore.entity.display.Rating;
import com.ctu.bookstore.mapper.display.CommentMapper;
import com.ctu.bookstore.repository.UserRepository;
import com.ctu.bookstore.repository.display.CommentRepository;
import com.ctu.bookstore.repository.display.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentResponse addComment(CommentRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Product product = productRepository.findById(request.getProductId()).orElseThrow();

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .product(product)
                .createdAt(LocalDateTime.now())
                .build();

        // tạo rating kèm comment
        Rating rating = Rating.builder()
                .stars(request.getStars())
                .comment(comment)
                .product(product)
                .build();

        comment.setRating(rating);

        commentRepository.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

    public List<CommentResponse> getComments(String productId) {
        return commentRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }
}
