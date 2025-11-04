package com.ctu.bookstore.mapper.display;

import com.ctu.bookstore.dto.request.display.ProductRequest;
import com.ctu.bookstore.dto.respone.display.ProductResponse;
import com.ctu.bookstore.entity.display.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, CommentMapper.class})
public interface ProductMapper {
    // Map khi tạo product
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imagesUrl", ignore = true)
    @Mapping(target = "comments", ignore = true) // ✅ bỏ qua vì FE không gửi comment khi tạo
    Product toProduct(ProductRequest request);

    // Map khi trả về cho FE
    @Mapping(source = "imagesUrl", target = "imagesUrl")
    @Mapping(source = "comments", target = "comments") // ✅ ánh xạ từ entity sang DTO
    ProductResponse toProductResponse(Product product);
}
