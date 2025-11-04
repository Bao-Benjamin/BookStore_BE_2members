//package com.ctu.bookstore.controller.Display;
//
//import com.ctu.bookstore.dto.request.display.CategoryRequest;
//import com.ctu.bookstore.dto.respone.display.CategoryRespone;
//import com.ctu.bookstore.entity.display.Category;
//import com.ctu.bookstore.mapper.display.CategoryMapper;
////import com.ctu.bookstore.service.display.CategoryService;
//import jakarta.persistence.SecondaryTable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Controller
//@RequestMapping("/category")
//public class CategoryController {
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private CategoryMapper categoryMapper;
//
//    @PostMapping
//    public ResponseEntity<CategoryRespone> createCategory(@RequestBody CategoryRequest category) {
//        Category createdCategory = categoryService.create(category);
//
//        CategoryRespone categoryRespone = categoryMapper.toCategoryRespone(createdCategory);
////        categoryRespone.setChildren(createdCategory.getChildCategory());
//        return new ResponseEntity<CategoryRespone>(categoryRespone, HttpStatus.CREATED);
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryRespone> getByID(@PathVariable("id") String categoryId){
//        Category category = categoryService.getByID(categoryId);
//        Set<CategoryRespone> childrenRespones = new HashSet<>();
//        System.out.println("category trong getByID controller:  "+category);
//        Set<Category> setCate = new HashSet<>();
//        if(!CollectionUtils.isEmpty(category.getChildCategory())){
//
//            childrenRespones = categoryMapper.toCategoryResponeSet(category.getChildCategory());
//        }
//        CategoryRespone categoryRespone = categoryMapper.toCategoryRespone(category);
//        categoryRespone.setChildren(childrenRespones);
//        return new ResponseEntity<CategoryRespone>(categoryRespone, HttpStatus.CREATED);
//    }
//}
