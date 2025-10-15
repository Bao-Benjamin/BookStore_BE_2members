package com.ctu.bookstore.service;

import com.ctu.bookstore.dto.request.AuthenticationRequest;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationService {
    private UserRepository userRepository;
    private UserService userService;

    public boolean checkLogin(AuthenticationRequest authenticationRequest){
        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean result = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        return result;
    }
}
