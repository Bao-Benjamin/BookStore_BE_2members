package com.ctu.bookstore.dto.respone.display;

import com.ctu.bookstore.entity.display.ProductImages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;
@Data
public class ProductResponse {
    String id;
    String nameProduct;
    Double importPrice;
    Double sellingPrice;
    Double salePrice;
    int quantity;
    String description;
    String author;
    String categoryName;
    Set<ProductImages> imagesUrl;
    Double averageStars;
    Set<CommentResponse> comments;
}
