//package com.ctu.bookstore.service.display;
//
//import com.ctu.bookstore.dto.request.display.CategoryRequest;
//import com.ctu.bookstore.dto.respone.display.CategoryRespone;
//import com.ctu.bookstore.entity.display.Category;
//import com.ctu.bookstore.exception.AppException;
//import com.ctu.bookstore.exception.ErrorCode;
//import com.ctu.bookstore.mapper.display.CategoryMapper;
//import com.ctu.bookstore.repository.display.CategoryRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@Service
//public class CategoryService {
//    @Autowired
//    private CategoryMapper categoryMapper;
//    @Autowired
//    private CategoryRepository categoryRepository;
////    public Category create(CategoryRequest request){
////        Category category = categoryMapper.toCategory(request);
////
////        return categoryRepository.save(category);
////    }
//    // Helper: Ném ngoại lệ khi không tìm thấy
//    private Category findByIdOrThrow(String id) {
//        return categoryRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Category not found with id: " + id));
//    }
//    @Transactional
//    public Category create(CategoryRequest request){
//        Category category = categoryMapper.toCategory(request);
//        if (request.getParentId() != null && !request.getParentId().isEmpty()) {
//            Category parent = categoryRepository.findById(request.getParentId())
//                    .orElseThrow(() -> new ResponseStatusException(
//                            HttpStatus.NOT_FOUND, "Category not found with id: " + request.getParentId()));
//
//            category.setParentCategory(parent);
//            // JPA tự quản lý quan hệ, nhưng để đồng bộ trong bộ nhớ:
//            // parent.getChildCategory().add(category);
//        }
//
//        Category savedCategory = categoryRepository.save(category);
//        return savedCategory;
//    }
//    public Category getByID(String id){
//        return  categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//    }
//
//
////    public List<CategoryRespone> getAllCategories() { // Đổi đầu ra là DTO
////        List<Category> entities = categoryRepository.findAll();
////        // Sử dụng Mapper để ánh xạ danh sách
////
////        return entities.stream().map(categoryMapper::toCategoryRespone).toList();
////        // LƯU Ý: Nếu bạn muốn trả về cấu trúc CÂY, bạn sẽ cần thêm logic xây dựng cây TẠI ĐÂY
////    }
//}
