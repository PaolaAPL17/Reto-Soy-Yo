package com.reto.soyyo.controllers;

import com.reto.soyyo.dtos.auth.LoginRequestDto;
import com.reto.soyyo.dtos.auth.LoginResponseDto;
import com.reto.soyyo.dtos.user.UserRequest;
import com.reto.soyyo.dtos.user.UserResponse;
import com.reto.soyyo.services.AuthService;
import com.reto.soyyo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Validated @RequestBody UserRequest userRequest) {
        UserResponse registeredUser = userService.createUser(userRequest);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Validated @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.authenticateUser(loginRequestDto);
        return ResponseEntity.ok(response);
    }
}