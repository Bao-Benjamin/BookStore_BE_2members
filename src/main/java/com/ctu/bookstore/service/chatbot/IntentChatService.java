package com.ctu.bookstore.service.chatbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IntentChatService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate rest;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(IntentChatService.class);

    public IntentChatService(RestTemplate rest) {
        this.rest = rest;
    }

    // 1️⃣ AI phân tích intent (JSON ONLY)
    public String analyzeIntent(String userMessage) {
        try {
            String prompt = buildPrompt(userMessage);

            ObjectNode body = mapper.createObjectNode();
            body.put("model", "gpt-4.1-mini");
            body.put("input", prompt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity =
                    new HttpEntity<>(mapper.writeValueAsString(body), headers);

            ResponseEntity<String> response = rest.exchange(
                    "https://api.openai.com/v1/responses",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            log.info("=== RAW OPENAI RESPONSE ===\n{}\n===========================",
                    response.getBody());

            return extractAiText(response.getBody());

        } catch (Exception e) {
            return "{\"intent\":\"unknown\"}";
        }
    }


    // 2️⃣ Router gọi API thật
//    public String routeIntent(String intentJson, String token) {
//        try {
//            JsonNode json = mapper.readTree(intentJson);
//            String intent = json.get("intent").asText();
//
//            return switch (intent) {
//                case "get_my_cart" ->
//                        callApi("http://localhost:8080/bookstore/carts/my-cart", token);
//
//                default -> "{\"type\":\"chat\",\"answer\":\"Tôi chưa rõ ý bạn.\"}";
//            };
//
//        } catch (Exception e) {
//            return "{\"type\":\"chat\",\"answer\":\"Lỗi xử lý intent.\"}";
//        }
//    }
    // 2️⃣ Router xử lý intent
    public String routeIntent(String intentJson, String token) {
        try {
            JsonNode json = mapper.readTree(intentJson);
            String intent = json.has("intent") ? json.get("intent").asText() : "unknown";

            switch (intent) {
                case "get_my_cart":
                    return callApi("http://localhost:8080/bookstore/carts/my-cart", token);

                case "get_my_orders":
                    return callApi("http://localhost:8080/bookstore/orders/user", token);

                default:
                    return "{\"type\":\"chat\",\"answer\":\"Tôi có thể giúp gì cho bạn?\"}";
            }

        } catch (Exception e) {
            return "Lỗi đọc intent: " + e.getMessage();
        }
    }


    // 3️⃣ Gọi API nội bộ
//    private String callApi(String url, String token) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + token);
//
//            HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<String> response =
//                    rest.exchange(url, HttpMethod.GET, entity, String.class);
//
//            return response.getBody();
//
//        } catch (Exception e) {
//            return "{\"type\":\"chat\",\"answer\":\"Lỗi gọi API nội bộ.\"}";
//        }
//    }
    // 3️⃣ Gọi API nội bộ
    private String callApi(String url, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, entity, String.class);

            // ✅ Nếu là API đơn hàng → chỉ lấy 5 đơn gần nhất
            if (url.contains("/orders/user")) {
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode resultArr = root.get("result");

                if (resultArr != null && resultArr.isArray()) {
                    ObjectNode newRoot = mapper.createObjectNode();
                    newRoot.put("type", "orders");

                    // Tạo mảng mới chỉ chứa tối đa 5 đơn
                    com.fasterxml.jackson.databind.node.ArrayNode limited = mapper.createArrayNode();
                    int limit = Math.min(5, resultArr.size());
                    for (int i = 0; i < limit; i++) {
                        limited.add(resultArr.get(i));
                    }

                    newRoot.set("orders", limited);

                    return mapper.writeValueAsString(newRoot);
                }
            }

            // các API khác trả nguyên body
            return response.getBody();
        } catch (Exception e) {
            return "Lỗi gọi API: " + e.getMessage();
        }
    }


    // 4️⃣ Build prompt tách intent
//    private String buildPrompt(String userMessage) {
//        return """
//                Bạn là hệ thống phân tích ý định người dùng.
//                CHỈ TRẢ JSON. KHÔNG TRẢ LỜI CHAT.
//
//                Nếu không hiểu → {"intent":"unknown"}
//
//                Các intent:
//                - get_my_cart
//                - add_to_cart
//                - remove_cart_item
//                - search_product
//                - checkout
//
//                User input: """ + userMessage;
//    }
    // 🧠 Build prompt để ép model trả JSON 100%
    private String buildPrompt(String userMessage) {
        String template = """
    Bạn là hệ thống phân tích ý định của người dùng.
    CHỈ TRẢ VỀ JSON, KHÔNG VIẾT THÊM CHỮ NÀO KHÁC.
    Nếu không hiểu thì trả về: {"intent":"unknown"}.

    Các intent hợp lệ:
    - get_my_cart: Lấy giỏ hàng của user
    - get_my_orders: Lấy danh sách đơn hàng của user
    - add_to_cart: Thêm sản phẩm vào giỏ
    - remove_cart_item: Xóa sản phẩm khỏi giỏ
    - search_product: Tìm sản phẩm
    - checkout: Thanh toán

    User input: %s

    Trả JSON ngay lập tức:
    """;

        return String.format(template, userMessage);
    }


    // 5️⃣ AI trả lời tự nhiên khi không có intent
    public String generateNormalChatReply(String userMessage) {
        try {
            ObjectNode body = mapper.createObjectNode();
            body.put("model", "gpt-4.1-mini");
            body.put("input",
                    "Bạn là chatbot cửa hàng sách, trả lời thân thiện, tự nhiên.\n"
                            + "User: " + userMessage);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity =
                    new HttpEntity<>(mapper.writeValueAsString(body), headers);

            ResponseEntity<String> response = rest.exchange(
                    "https://api.openai.com/v1/responses",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return extractAiText(response.getBody());

        } catch (Exception e) {
            return "Xin lỗi, mình đang gặp chút sự cố.";
        }
    }

    // 6️⃣ Extract text từ OpenAI Responses API
    private String extractAiText(String fullJson) throws Exception {
        JsonNode root = mapper.readTree(fullJson);
        JsonNode output = root.get("output");

        JsonNode content = output.get(0).get("content");
        return content.get(0).get("text").asText();
    }
}
