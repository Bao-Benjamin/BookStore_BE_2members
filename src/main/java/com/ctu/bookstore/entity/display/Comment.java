package com.ctu.bookstore.entity.display;

import com.ctu.bookstore.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @MongoId
    String id;

    String productId;
    String userId;
    String username;
    String comment;
    Integer rating;        // 1 - 5
    Instant createdAt;
}
