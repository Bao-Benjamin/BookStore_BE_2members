package com.ctu.bookstore.service.display;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ctu.bookstore.entity.display.ProductImages;
import com.ctu.bookstore.repository.display.ProductImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImagesService {

    private final Cloudinary cloudinary;
    private final ProductImagesRepository productImagesRepository;

    /**
     * Upload ảnh lên Cloudinary và lưu vào database
     */
    public ProductImages uploadImage(MultipartFile file) throws IOException {

        // Tạo public id cho cloudinary
        String publicId = UUID.randomUUID().toString();

        // Upload ảnh
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", publicId,
                        "folder", "bookstore-images"
                )
        );

        // Lấy secure URL
        String imageUrl = (String) uploadResult.get("secure_url");

        // Tạo entity ProductImages
        ProductImages image = new ProductImages();
        image.setPublicId(publicId);
        image.setUrl(imageUrl);

        // Lưu DB và trả về
        return productImagesRepository.save(image);
    }

    /**
     * Xóa ảnh: Cloudinary + Database
     */
    public void deleteImage(String imageId) throws IOException {
        ProductImages img = productImagesRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ảnh"));

        cloudinary.uploader().destroy(img.getPublicId(), ObjectUtils.emptyMap());

        productImagesRepository.deleteById(imageId);
    }
}
