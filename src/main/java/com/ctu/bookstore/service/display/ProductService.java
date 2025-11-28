package com.ctu.bookstore.service.display;

import com.ctu.bookstore.dto.request.display.ProductRequest;
import com.ctu.bookstore.dto.respone.display.ProductResponse;
import com.ctu.bookstore.elasticsearch.ProductSearchService;
import com.ctu.bookstore.entity.display.Category;
import com.ctu.bookstore.entity.display.Product;
import com.ctu.bookstore.entity.display.ProductImages;
import com.ctu.bookstore.mapper.display.ProductMapper;
import com.ctu.bookstore.repository.display.CategoryRepository;
import com.ctu.bookstore.repository.display.ProductImagesRepository;
import com.ctu.bookstore.repository.display.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductImagesService productImagesService;
    private final ProductRepository productRepository;
    private final ProductSearchService productSearchService;

    // -----------------------------------------------------
    // TẠO PRODUCT
    // -----------------------------------------------------
    public Product create(ProductRequest request) throws IOException {

        Product product = productMapper.toProduct(request);

        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + request.getCategoryId()));
            product.setCategory(category);
        }
        Set<ProductImages> imagesSet = new HashSet<>();

        if (request.getImages() != null) {
            for (MultipartFile file : request.getImages()) {

                if (!file.isEmpty()) {
                    try {
                        // Upload lên Cloudinary → trả về entity ProductImages
                        ProductImages image = productImagesService.uploadImage(file);

                        // gắn quan hệ
                        image.setProduct(product);

                        imagesSet.add(image);

                    } catch (IOException e) {
                        // Xử lý lỗi upload ảnh cụ thể, tránh dừng toàn bộ quá trình
                        System.err.println("Lỗi upload ảnh: " + e.getMessage());
                        // Có thể throw lại RuntimeException hoặc log lỗi và tiếp tục
                    }
                }
            }

            product.setImagesUrl(imagesSet);
        }

        product = productRepository.save(product);

        // ❗ Đồng bộ lên Elasticsearch
        productSearchService.indexProduct(product);

        return product;
    }

    // -----------------------------------------------------
    // LẤY TẤT CẢ
    // -----------------------------------------------------
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    // -----------------------------------------------------
    // LẤY THEO ID
    // -----------------------------------------------------
    public ProductResponse findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));

        return productMapper.toProductResponse(product);
    }

    // -----------------------------------------------------
    // UPDATE PRODUCT
    // -----------------------------------------------------
    public ProductResponse update(String id, ProductRequest request) throws IOException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));

        // cập nhật thông tin sản phẩm
        if (request.getNameProduct() != null && !request.getNameProduct().isBlank()) {
            product.setNameProduct(request.getNameProduct());
        }

        if (request.getImportPrice() != null) {
            product.setImportPrice(request.getImportPrice());
        }

        if (request.getSellingPrice() != null) {
            product.setSellingPrice(request.getSellingPrice());
        }

        if (request.getSalePrice() != null) {
            product.setSalePrice(request.getSalePrice());
        }

        // quantity là kiểu nguyên, mặc định = 0 nếu người dùng không gửi →
        // ta cần phân biệt xem người dùng có gửi thật hay không.
        // Cách đơn giản nhất: đổi kiểu quantity trong ProductRequest thành `Integer` thay vì `int`
        if (request.getQuantity() != 0) {
            product.setQuantity(request.getQuantity());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + request.getCategoryId()));
            product.setCategory(category);
        }

        // xử lý upload ảnh mới
        if (request.getImages() != null) {

            boolean hasRealImage = request.getImages()
                    .stream()
                    .anyMatch(img -> !img.isEmpty());

            if (hasRealImage) {

                // Xóa ảnh cũ: xóa Cloudinary + xóa DB
                for (ProductImages oldImg : product.getImagesUrl()) {
                    productImagesService.deleteImage(oldImg.getId());
                }

                Set<ProductImages> newImages = new HashSet<>();

                for (MultipartFile file : request.getImages()) {
                    if (!file.isEmpty()) {
                        ProductImages img = productImagesService.uploadImage(file);
                        img.setProduct(product);
                        newImages.add(img);
                    }
                }

                product.getImagesUrl().clear();
                product.getImagesUrl().addAll(newImages);
            }
        }

        Product updated = productRepository.save(product);

        // ❗ Đồng bộ Elasticsearch
        productSearchService.indexProduct(updated);

        return productMapper.toProductResponse(updated);
    }

    // -----------------------------------------------------
    // DELETE PRODUCT
    // -----------------------------------------------------
    public void delete(String id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));

        // Xóa toàn bộ ảnh liên quan
        if (product.getImagesUrl() != null) {
            for (ProductImages img : product.getImagesUrl()) {
                try {
                    productImagesService.deleteImage(img.getId());
                } catch (Exception ignored) {
                }
            }
        }

        // Xóa sản phẩm trong DB
        productRepository.delete(product);

        // ❗ Xóa trên Elasticsearch
        productSearchService.deleteProduct(id);
    }

}
