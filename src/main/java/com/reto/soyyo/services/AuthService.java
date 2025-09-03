package com.reto.soyyo.services;

import com.reto.soyyo.dtos.auth.LoginRequestDto;
import com.reto.soyyo.dtos.auth.LoginResponseDto;
import com.reto.soyyo.dtos.user.UserRequest;
import com.reto.soyyo.dtos.user.UserResponse;
import com.reto.soyyo.dtos.user.UserMapper;
import com.reto.soyyo.models.User;
import com.reto.soyyo.repositories.UserRepository;
import com.reto.soyyo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new IllegalArgumentException("Email already taken");
        }
        User user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .build();
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return new LoginResponseDto(token);
    }
}