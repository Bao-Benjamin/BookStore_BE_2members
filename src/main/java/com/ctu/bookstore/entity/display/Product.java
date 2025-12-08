package com.ctu.bookstore.entity.display;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    String id;
    String nameProduct;
    Double importPrice;
    Double sellingPrice;
    Double salePrice;
    int quantity;
    String description;
    String author;
    Instant createDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
//    @JsonManagedReference
    Set<ProductImages> imagesUrl = new HashSet<>();
//    @Builder.Default
//    @ElementCollection
//    @CollectionTable(
//            name = "product_comment_ids",
//            joinColumns = @JoinColumn(name = "product_id")
//    )
//    @Column(name = "comment_id")
//    List<String> commentId = new ArrayList<>();

    // Helper method để quản lý mối quan hệ hai chiều



    // Helper method (giữ nguyên để đảm bảo mối quan hệ hai chiều)
    public void addImage(ProductImages image) {
        // Kiểm tra an toàn, mặc dù đã khởi tạo ở trên, đây là best practice
        if (this.imagesUrl == null) {
            this.imagesUrl = new HashSet<>();
        }
        this.imagesUrl.add(image);
        image.setProduct(this);
    }


}
