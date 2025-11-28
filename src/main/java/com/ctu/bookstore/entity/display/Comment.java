package com.ctu.bookstore.entity.display;

import com.ctu.bookstore.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Entity
@Getter //recommend using @Getter and @Setter cho entity(google for more). One reason is it easy to manage
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(columnDefinition = "TEXT")
    String content;

    LocalDateTime createdAt;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Rating rating;

    // Người bình luận
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    // Sản phẩm được bình luận
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;


}
