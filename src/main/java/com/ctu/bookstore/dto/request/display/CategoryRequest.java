package com.ctu.bookstore.dto.request.display;

import com.ctu.bookstore.entity.display.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class CategoryRequest {
    @NotNull(message = "Tên danh mục không được để trống")
    @Size(min = 2, max = 100, message = "Tên danh mục phải từ 2 đến 100 ký tự")
    private String nameCategory;

    // ID của danh mục cha (Optional).
    // Nếu tạo danh mục con, người dùng/UI sẽ cung cấp ID này.
    private String parentId;
    private Category children;
}
