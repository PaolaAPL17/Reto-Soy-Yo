package com.reto.soyyo.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "The email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "The password is required")
    @Size(min = 8, max = 128, message = "The password must be at least 8 characters long")
    private String password;
}
