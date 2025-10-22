package com.ctu.bookstore.service.display;

import com.ctu.bookstore.dto.request.display.ProductRequest;
import com.ctu.bookstore.dto.respone.display.ProductResponse;
import com.ctu.bookstore.entity.display.Product;
import com.ctu.bookstore.entity.display.ProductImages;
import com.ctu.bookstore.mapper.display.ProductMapper;
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
    private final ProductImagesService productImagesService;
    private final ProductRepository productRepository;
    public Product create(ProductRequest request) throws IOException {
        Product product = productMapper.toProduct(request);

        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }

        Set<ProductImages> imagesSet = new HashSet<>();

        if (request.getImages() != null) {
            for (MultipartFile file : request.getImages()) {
                if (!file.isEmpty()) {
                    try {
                    // Upload ảnh lên Cloudinary và lấy link
                    String imageUrl = productImagesService.uploadImage(file);

                    // Tạo đối tượng ProductImages và set đủ thông tin
                    ProductImages image = new ProductImages();
                    image.setUrl(imageUrl);
                    // Thiết lập mối quan hệ hai chiều bằng helper method
//                        product.addImage(image);

                        imagesSet.add(image);
//                    image.setProduct(product);
//
//                    imagesSet.add(image);
                    System.out.println("Đã xử lý ảnh Cloudinary: " + imageUrl);
                    } catch (IOException e) {
                        // Xử lý lỗi upload ảnh cụ thể, tránh dừng toàn bộ quá trình
                        System.err.println("Lỗi upload ảnh: " + e.getMessage());
                        // Có thể throw lại RuntimeException hoặc log lỗi và tiếp tục
                    }
                }
            }
            product.setImagesUrl(imagesSet);
        }

//        product.setImagesUrl(imagesSet);

        return productRepository.save(product);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public ProductResponse findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));

        return productMapper.toProductResponse(product);
    }
    public ProductResponse update(String id, ProductRequest request) throws IOException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));

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


        if (request.getImages() != null) {
            // check xem trong list có file nào không rỗng không
            boolean hasRealImage = request.getImages().stream().anyMatch(img -> !img.isEmpty());

            if (hasRealImage) {
                // Xóa ảnh cũ và thêm ảnh mới
                Set<ProductImages> newImages = new HashSet<>();
                Set<ProductImages> productImages = product.getImagesUrl();
                for(ProductImages oldImage : productImages){
                    productImagesService.deleteImage(oldImage.getId());
                }
                for (MultipartFile file : request.getImages()) {
                    if (!file.isEmpty()) {
                        String imageUrl = productImagesService.uploadImage(file);

                        ProductImages newImg = new ProductImages();
                        newImg.setUrl(imageUrl);
                        newImg.setProduct(product);
                        newImages.add(newImg);
                    }
                }

                product.getImagesUrl().clear();
                product.getImagesUrl().addAll(newImages);
            }
        }


        Product updated = productRepository.save(product);


        return productMapper.toProductResponse(updated);
    }
}


