package com.reto.soyyo.dtos.challenge;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChallengeRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100)
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 500)
        String description
) {
}
