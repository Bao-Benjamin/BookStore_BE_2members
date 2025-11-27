package com.ctu.bookstore.controller.conversation;


import com.ctu.bookstore.dto.request.chat.ConversationRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.respone.chat.ConversationResponse;
import com.ctu.bookstore.service.chat.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationController {
    @Autowired
    ConversationService conversationService;

    @PostMapping("/create")
    ApiRespone<ConversationResponse> createConversation(@RequestBody @Valid ConversationRequest request) {
        return ApiRespone.<ConversationResponse>builder()
                .result(conversationService.create(request))
                .build();
    }
    @PostMapping("/create-default")
    ApiRespone<ConversationResponse> createConversation() {
        return ApiRespone.<ConversationResponse>builder()
                .result(conversationService.createDefault())
                .build();
    }

    @GetMapping("/my-conversations")
    ApiRespone<List<ConversationResponse>> myConversations() {
        return ApiRespone.<List<ConversationResponse>>builder()
                .result(conversationService.myConversations())
                .build();
    }
}
