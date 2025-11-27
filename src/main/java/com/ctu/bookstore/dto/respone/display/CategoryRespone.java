package com.ctu.bookstore.dto.respone.display;

import com.ctu.bookstore.entity.display.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryRespone {
    private String id;
    private String nameCategory;
//    private String slug;

    // Thông tin cha
    private String parentId ;
    private String parentName ; // Giúp UI hiển thị dễ dàng

    // Danh sách các danh mục con (giúp xây dựng cấu trúc cây/lồng nhau)
    private Set<CategoryRespone> children = new HashSet<>();
}
