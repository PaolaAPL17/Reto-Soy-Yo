package com.reto.soyyo.dtos.user;

import com.reto.soyyo.models.User;
import com.reto.soyyo.models.Role;

public class UserMapper {

    public static User toEntity(UserRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRol(Role.valueOf(request.rol().toUpperCase()));
        return user;
    }

    public static UserResponse toDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
