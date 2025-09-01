package com.reto.soyyo.dtos.user;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
