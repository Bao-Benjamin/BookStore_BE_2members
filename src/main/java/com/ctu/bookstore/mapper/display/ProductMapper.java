package com.ctu.bookstore.mapper.display;

import com.ctu.bookstore.dto.request.display.ProductRequest;
import com.ctu.bookstore.dto.respone.display.ProductResponse;
import com.ctu.bookstore.entity.display.Product;
import com.ctu.bookstore.entity.display.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, CommentMapper.class})
public interface ProductMapper {
    // Map khi tạo product
    // Không cần gán ID thủ công trong Service nếu dùng @GeneratedValue/Builder/PrePersist trong Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imagesUrl", ignore = true)
    @Mapping(target = "comments", ignore = true) // ✅ bỏ qua vì FE không gửi comment khi tạo
    @Mapping(target = "ratings", ignore = true) // ⚠️ Thêm dòng này để tránh lỗi khi entity có thêm ratings
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductRequest request);

    // Map khi trả về cho FE
    @Mapping(source = "imagesUrl", target = "imagesUrl")
    @Mapping(source = "comments", target = "comments") // ✅ ánh xạ từ entity sang DTO
    @Mapping(target = "averageStars", expression = "java(calculateAverageStars(product))")
    @Mapping(target = "categoryName", source = "category.nameCategory")
    ProductResponse toProductResponse(Product product);

    default Double calculateAverageStars(Product product) {
        if (product.getRatings() == null || product.getRatings().isEmpty()) return 0.0;
        return product.getRatings().stream()
                .mapToInt(Rating::getStars)
                .average()
                .orElse(0.0);
    }
}
