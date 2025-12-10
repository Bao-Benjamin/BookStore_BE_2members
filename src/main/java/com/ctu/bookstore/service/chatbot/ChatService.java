//package com.ctu.bookstore.service.chatbot;
//
//
////import com.ctu.bookstore.dto.request.chatbot.ChatRequest;
//import com.openai.client.OpenAIClient;
//import com.openai.models.responses.Response;
//import com.openai.models.responses.ResponseCreateParams;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service
////@AllArgsConstructor
////@NoArgsConstructor
//@RequiredArgsConstructor
//public class ChatService {
//    private  final OpenAIClient openAIClient;
//
//    public String ask(String message) {
//
//        ResponseCreateParams params = ResponseCreateParams.builder()
//                .model("gpt-4.1-mini") // hoặc model bạn muốn
//                .input(message)
//                .build();
//
//        Response response = openAIClient.responses().create(params);
//
//        String answer = response
//                .output()
//                .get(0)
//                .message()
//                .get()
//                .content()
//                .get(0)
//                .outputText()
//                .get()
//                .text();
//
//        return answer;
////        return response.toString(); // giống docs
//    }
//}
