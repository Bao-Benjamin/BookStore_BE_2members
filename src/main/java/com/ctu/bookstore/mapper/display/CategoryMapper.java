package com.ctu.bookstore.mapper.display;

import com.ctu.bookstore.dto.request.display.CategoryRequest;
import com.ctu.bookstore.dto.respone.display.CategoryRespone;
import com.ctu.bookstore.entity.display.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "parentCategory", ignore = true) // Sẽ được Service tìm và gán
    @Mapping(target = "childCategory", ignore = true)
    Category toCategory(CategoryRequest dto);
    @Mapping(source = "parentCategory.id", target = "parentId")
    // Ánh xạ `parentCategory.nameCategory` vào `parentName`
    @Mapping(source = "parentCategory.nameCategory", target = "parentName")
    @Mapping(target = "children", ignore = true)
    CategoryRespone toCategoryRespone(Category category);

    Set<CategoryRespone> toCategoryResponeSet(Set<Category> categories);

}
