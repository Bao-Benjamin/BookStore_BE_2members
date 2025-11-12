package com.ctu.bookstore.configuration;

import com.ctu.bookstore.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {
            "/users","/user",
            "/auth/token", "/auth/introspect" ,"/auth/logout",
            "/images/upload", "/products",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"

    };
    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

//    @Bean
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity)
//            throws Exception {
//        // Áp dụng cấu hình CORS
//        httpSecurity.cors(Customizer.withDefaults()); // <--- DÒNG QUAN TRỌNG NHẤT
//        httpSecurity.authorizeHttpRequests(authorize ->
//                authorize.requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()
//                        .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINTS).permitAll()
////                        .requestMatchers(HttpMethod.GET, "/user").hasRole(Role.ADMIN.name())
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html"
//                        ).permitAll()
//                        .requestMatchers(HttpMethod.POST, "/images/upload", "/products", "/api/comments").permitAll()
//                        .anyRequest().authenticated());
//
//        httpSecurity.csrf(csrf -> csrf.disable());
//
//        httpSecurity.oauth2ResourceServer(oauth2 ->
//                oauth2.jwt(jwtConfigurer -> jwtConfigurer
//                        .decoder(customJwtDecoder)
//                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//        );
//
//        return httpSecurity.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // ✅ Cấu hình CORS phải khai báo trước authorizeHttpRequests
        http.cors(Customizer.withDefaults());

        // ✅ Tắt CSRF vì đang dùng API REST
        http.csrf(csrf -> csrf.disable());

        // ✅ Cấu hình phân quyền
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // cho phép preflight request
                .anyRequest().authenticated()
        );

        // ✅ Cấu hình JWT
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        return http.build();
    }
//    // Bean để cấu hình CORS
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    // Cho phép CORS từ các origin cần thiết
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",  // Vite
                "http://127.0.0.1:5500"   // Live Server
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix(""); // bỏ prefix ROLE_

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
    }




