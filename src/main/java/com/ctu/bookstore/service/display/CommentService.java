package com.ctu.bookstore.service.display;

import com.ctu.bookstore.dto.request.display.CommentRequest;
import com.ctu.bookstore.dto.respone.display.CommentResponse;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.entity.display.Comment;
import com.ctu.bookstore.entity.payment.UserOrder;
import com.ctu.bookstore.enums.OrderStatus;
import com.ctu.bookstore.mapper.display.CommentMapper;
import com.ctu.bookstore.repository.UserRepository;
import com.ctu.bookstore.repository.display.CommentRepository;
import com.ctu.bookstore.repository.display.ProductRepository;
import com.ctu.bookstore.repository.payment.UserOrderRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentService {
    CommentRepository commentRepository;
    UserOrderRepository userOrderRepository;
    ProductRepository productRepository; // bạn đã có sẵn
    UserRepository userRepository;       // bạn đã có sẵn (nếu cần lấy username)
    CommentMapper commentMapper;
    @Transactional
    public CommentResponse createComment(String productId, CommentRequest request) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tạo comment
        Comment comment = Comment.builder()
                .productId(productId)
                .comment(request.getComment())
                .rating(request.getRating())
                .createdAt(Instant.now())
                .userId(user.getId())
                .username(user.getUsername())
                .build();

        // Lưu comment trước để có id
        Comment savedComment = commentRepository.save(comment);

        // Đảm bảo list không null (phòng khi dữ liệu DB cũ)
//        if (product.getCommentId() == null) {
//            product.setCommentId(new ArrayList<>());
//        }
//        System.out.println("savedComment.getId() trong comment service: "+savedComment.getId());
//        product.getCommentId().add(savedComment.getId());
//        var prod = productRepository.save(product); // lưu lại product
//        System.out.println("product commentid trong comment service "+ prod.getCommentId());

        return commentMapper.toCommentResponse(savedComment);
    }

    public List<Comment> getCommentOfProduct(String productId){
        return commentRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }


}
