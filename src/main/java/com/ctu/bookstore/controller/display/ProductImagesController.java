package com.ctu.bookstore.controller.display;

import com.ctu.bookstore.entity.display.ProductImages;
import com.ctu.bookstore.service.display.ProductImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ProductImagesController {

    private final ProductImagesService productImagesService;

    /**
     * API Upload ảnh
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File không được rỗng!", HttpStatus.BAD_REQUEST);
        }

        try {
            ProductImages savedImage = productImagesService.uploadImage(file);
            return new ResponseEntity<>(savedImage, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi upload ảnh: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API xóa ảnh
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") String id) {
        try {
            productImagesService.deleteImage(id);
            return new ResponseEntity<>("Xóa ảnh thành công!", HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi xóa ảnh: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
