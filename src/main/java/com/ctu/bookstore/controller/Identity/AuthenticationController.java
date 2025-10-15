package com.ctu.bookstore.controller;

import com.ctu.bookstore.dto.request.AuthenticationRequest;
import com.ctu.bookstore.dto.request.IntrospectRequest;
import com.ctu.bookstore.dto.request.LogoutRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.respone.AuthenticationRespone;
import com.ctu.bookstore.dto.respone.IntrospectRespone;
import com.ctu.bookstore.entity.User;
import com.ctu.bookstore.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/token")
    public ApiRespone<AuthenticationRespone>authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationRespone result = authenticationService.authenticate(authenticationRequest);

        return ApiRespone.<AuthenticationRespone>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiRespone<IntrospectRespone>introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        IntrospectRespone result = authenticationService.instrospect(introspectRequest);

        return ApiRespone.<IntrospectRespone>builder()
                .result(result)
                .build();
    }
    @PostMapping("/logout")
    public ApiRespone<Void>logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);

        return ApiRespone.<Void>builder()
                .build();
    }
}
