//package com.ctu.bookstore.controller.chatbot;
//
//import com.ctu.bookstore.service.chatbot.IntentChatService;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/chat")
//public class IntentChatController {
//
//    private final IntentChatService chatService;
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    public IntentChatController(IntentChatService chatService) {
//        this.chatService = chatService;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> chat(
//            @RequestHeader(value = "Authorization", required = false) String bearer,
//            @RequestBody ChatRequest request
//    ) {
//        try {
//            if (bearer == null || !bearer.startsWith("Bearer ")) {
//                return ResponseEntity.status(401).body("Thiếu Bearer Token");
//            }
//
//            String token = bearer.replace("Bearer ", "").trim();
//            String userMsg = request.message();
//
//            // 1) Gọi AI để lấy intent
//            String intentJson = chatService.analyzeIntent(userMsg);
//
//            JsonNode intentNode = mapper.readTree(intentJson);
//            String intent = intentNode.has("intent")
//                    ? intentNode.get("intent").asText()
//                    : "unknown";
//
//            // 2) Nếu có intent → gọi API thật
//            if (!intent.equals("unknown")) {
//
//                String apiResult = chatService.routeIntent(intentJson, token);
//
//                try {
//                    // Nếu API trả JSON → gửi thẳng ra FE
//                    return ResponseEntity.ok(mapper.readTree(apiResult));
//                } catch (Exception e) {
//                    // Nếu API trả text
//                    return ResponseEntity.ok(Map.of(
//                            "type", "chat",
//                            "answer", apiResult
//                    ));
//                }
//            }
//
//            // 3) Nếu intent = unknown → ChatGPT trả lời tự nhiên
//            String reply = chatService.generateNormalChatReply(userMsg);
//
//            return ResponseEntity.ok(Map.of(
//                    "type", "chat",
//                    "answer", reply
//            ));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500)
//                    .body("Lỗi xử lý chat: " + e.getMessage());
//        }
//    }
//
//    public record ChatRequest(String message) {}
//}
