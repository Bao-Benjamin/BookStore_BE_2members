package com.ctu.bookstore.controller.display;

import com.ctu.bookstore.dto.request.display.ProductRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.respone.display.ProductResponse;
import com.ctu.bookstore.entity.display.Product;
import com.ctu.bookstore.mapper.display.ProductMapper;
import com.ctu.bookstore.service.display.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    ProductMapper productMapper ;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<ProductResponse> create (@ModelAttribute ProductRequest productRequest
                                        ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Product newProduct = productService.create(productRequest);

        return ApiRespone.<ProductResponse>builder()
                .result(productMapper.toProductResponse(newProduct))
                .build();
    }

    @GetMapping
    public ApiRespone<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.findAll();
        return ApiRespone.<List<ProductResponse>>builder()
                .result(products)
                .build();
    }

    @GetMapping("/{id}")
    public ApiRespone<ProductResponse> getProductById(@PathVariable String id) {
        ProductResponse product = productService.findById(id);
        return ApiRespone.<ProductResponse>builder()
                .result(product)
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<ProductResponse> updateProduct(
            @PathVariable String id,
            @ModelAttribute ProductRequest productRequest) throws IOException {

        ProductResponse updatedProduct = productService.update(id, productRequest);

        return ApiRespone.<ProductResponse>builder()
                .result(updatedProduct)
                .build();
    }
}
