// package com.ctu.bookstore.elasticsearch;

// import com.ctu.bookstore.dto.respone.ApiRespone;
// import com.ctu.bookstore.dto.respone.display.PageResponse;
// import com.ctu.bookstore.dto.respone.display.ProductResponse;
// import com.ctu.bookstore.entity.display.Product;
// import com.ctu.bookstore.mapper.display.ProductMapper;
// import com.ctu.bookstore.repository.display.ProductRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.Page;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Objects;
// import java.util.stream.Collectors;
// import java.util.Map;
// import java.util.stream.Collectors;


// @RestController
// @RequestMapping("/search")
// @RequiredArgsConstructor
// public class ProductSearchController {
//     private final ProductSearchService productSearchService;
//     private final ProductRepository productRepository;

//     private final ProductMapper productMapper;

//     // === 1) Search sản phẩm theo tên ===
// //    @GetMapping
// //    public List<ProductDocument> search(@RequestParam String keyword) {
// //        return productSearchService.searchByName(keyword);
// //    }
// //    @GetMapping
// //    public ApiRespone<List<ProductResponse>> search(@RequestParam String keyword) {
// //        // 1. Gọi ES để tìm theo tên (chỉ có id, name, sellingPrice,...)
// //        List<ProductDocument> docs = productSearchService.searchByName(keyword);
// //
// //        if (docs.isEmpty()) {
// //            return ApiRespone.<List<ProductResponse>>builder()
// //                    .result(List.of())   // trả về list rỗng
// //                    .build();
// //        }
// //
// //        // 2. Lấy list id từ ES
// //        List<String> ids = docs.stream()
// //                .map(ProductDocument::getId)
// //                .toList();
// //
// //        // 3. Query MySQL lấy đầy đủ Product theo list id
// //        List<Product> products = productRepository.findAllById(ids);
// //
// //        // 4. Vì findAllById KHÔNG đảm bảo đúng thứ tự như ES,
// //        //    nên cần map lại theo đúng thứ tự docs.
// //        Map<String, Product> productById = products.stream()
// //                .collect(Collectors.toMap(Product::getId, p -> p));
// //
// //        List<ProductResponse> responses = ids.stream()
// //                .map(productById::get)              // lấy Product theo id
// //                .filter(Objects::nonNull)           // tránh null nếu có id không tồn tại
// //                .map(productMapper::toProductResponse) // map sang DTO
// //                .toList();
// //
// //        return ApiRespone.<List<ProductResponse>>builder()
// //                .result(responses)
// //                .build();
// //    }
//     @GetMapping
//     public ApiRespone<PageResponse<ProductResponse>> search(
//             @RequestParam String keyword,
//             @RequestParam(defaultValue = "1") int page,
//             @RequestParam(defaultValue = "6") int size
//     ) {
//         // 1. Gọi ES search có phân trang
//         Page<ProductDocument> docPage = productSearchService.searchByName(keyword, page, size);

//         // 2. Lấy list id theo đúng thứ tự ES
//         List<String> ids = docPage.getContent().stream()
//                 .map(ProductDocument::getId)
//                 .toList();

//         if (ids.isEmpty()) {
//             PageResponse<ProductResponse> empty = PageResponse.<ProductResponse>builder()
//                     .currentPage(page)
//                     .pageSize(size)
//                     .totalPages(0)
//                     .totalElements(0)
//                     .data(List.of())
//                     .build();

//             return ApiRespone.<PageResponse<ProductResponse>>builder()
//                     .result(empty)
//                     .build();
//         }

//         // 3. Query MySQL lấy đầy đủ Product theo list id
//         List<Product> products = productRepository.findAllById(ids);

//         // 4. Map về Map<id, Product> để giữ đúng thứ tự id từ ES
//         Map<String, Product> productById = products.stream()
//                 .collect(Collectors.toMap(Product::getId, p -> p));

//         // 5. Sắp xếp lại theo thứ tự ids + map sang DTO
//         List<ProductResponse> data = ids.stream()
//                 .map(productById::get)
//                 .filter(Objects::nonNull)
//                 .map(productMapper::toProductResponse)
//                 .toList();

//         // 6. Gói vào PageResponse
//         PageResponse<ProductResponse> pageResponse = PageResponse.<ProductResponse>builder()
//                 .currentPage(page)
//                 .pageSize(docPage.getSize())
//                 .totalPages(docPage.getTotalPages())
//                 .totalElements(docPage.getTotalElements())
//                 .data(data)
//                 .build();

//         return ApiRespone.<PageResponse<ProductResponse>>builder()
//                 .result(pageResponse)
//                 .build();
//     }


//     // === 2) Đồng bộ toàn bộ sản phẩm của DB lên ES ===
//     @PostMapping("/sync")
//     public String syncAll() {
//         List<Product> allProducts = productRepository.findAll();
//         productSearchService.syncAllProducts(allProducts);
//         return "Đồng bộ " + allProducts.size() + " sản phẩm lên Elasticsearch thành công!";
//     }

//     // === 3) Đồng bộ 1 sản phẩm theo ID ===
//     // HIỆN TẠI KHÔNG DÙNG TỚI VÌ KHI TẠO PRODUCT THÌ TRONG SERVICE CỦA PRODUCT ĐÃ CÓ GỌI METHOD TỰ ĐỒNG BỘ LÊN ELASTIC
//     @PostMapping("/index/{id}")
//     public String indexOne(@PathVariable String id) {
//         Product product = productRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));

//         productSearchService.indexProduct(product);
//         return "Indexed product id = " + id;
//     }

// }
