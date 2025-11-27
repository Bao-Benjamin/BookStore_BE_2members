package com.ctu.bookstore.entity.display;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;


@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "cart_item",
        uniqueConstraints = {
                // Đảm bảo không có hai CartItem nào có cùng cart_id VÀ cùng product_id
                @UniqueConstraint(columnNames = {"cart_id", "product_id"})
        }
)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonIgnore
    Cart cart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    @Fetch(FetchMode.JOIN)
    Product product ;
    int quatity;
}
