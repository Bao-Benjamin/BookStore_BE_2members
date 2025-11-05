package com.ctu.bookstore.dto.request.display;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    String content;      // Có thể null
    int stars;           // Bắt buộc
    String productId;    // Liên kết với sản phẩm
}
