package com.ctu.bookstore.entity.display;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter //recommend using @Getter and @Setter cho entity(google for more). One reason is it easy to manage
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    int stars; // từ 1 đến 5

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
}
