package com.ctu.bookstore.elasticsearch;

import com.ctu.bookstore.entity.display.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository searchRepository;

    // Đồng bộ 1 sản phẩm vào Elasticsearch
    public void indexProduct(Product product) {

        Double averageStars = 0.0;
        if (product.getRatings() != null && !product.getRatings().isEmpty()) {
            averageStars = product.getRatings()
                    .stream()
                    .mapToInt(r -> r.getStars())
                    .average()
                    .orElse(0.0);
        }

        ProductDocument doc = ProductDocument.builder()
                .id(product.getId())
                .nameProduct(product.getNameProduct())
                .sellingPrice(product.getSellingPrice())
                .averageStars(averageStars)
                .build();

        searchRepository.save(doc);
    }

    // Xóa khỏi ES khi xóa product trong DB
    public void deleteProduct(String productId) {
        searchRepository.deleteById(productId);
    }

    // Tìm kiếm theo tên
    public List<ProductDocument> searchByName(String keyword) {
        return searchRepository.findByNameProductContainingIgnoreCase(keyword);
    }

    // Đồng bộ toàn bộ sản phẩm từ DB vào ES
    public void syncAllProducts(List<Product> products) {
        searchRepository.deleteAll();
        for (Product p : products) {
            indexProduct(p);
        }
    }
}
