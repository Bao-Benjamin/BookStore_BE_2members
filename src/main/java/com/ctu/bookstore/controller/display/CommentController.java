package com.ctu.bookstore.controller.display;

import com.ctu.bookstore.dto.request.display.CommentRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.respone.display.CommentResponse;
import com.ctu.bookstore.service.display.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ApiRespone<CommentResponse> addComment(@RequestBody CommentRequest request, Authentication authentication) {
        return ApiRespone.<CommentResponse>builder()
                .result(commentService.addComment(request, authentication))
                .build();
    }

    @GetMapping("/{productId}")
    public ApiRespone<List<CommentResponse>> getComments(@PathVariable String productId) {
        // gọi hàm builder(được tự tạo) có kiểu là generic vì nó nằm trong class generic
        return ApiRespone.<List<CommentResponse>>builder()
                .result(commentService.getComments(productId))
                .build();
    }
}
