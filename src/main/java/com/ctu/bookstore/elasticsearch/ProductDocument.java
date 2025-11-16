package com.ctu.bookstore.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductDocument {

    @Id
    private String id;

    private String nameProduct;

    private Double sellingPrice;

    private Double averageStars;
}
