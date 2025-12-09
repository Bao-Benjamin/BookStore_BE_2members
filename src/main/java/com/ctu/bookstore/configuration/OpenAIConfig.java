package com.ctu.bookstore.configuration;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    public OpenAIClient openAIClient() {
        System.out.println(">>> LOADED API KEY = " + apiKey); // debug
        return OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public String getApiKey() {
        return apiKey;
    }
}

