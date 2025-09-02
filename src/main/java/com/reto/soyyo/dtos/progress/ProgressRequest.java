package com.reto.soyyo.dtos.progress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ProgressRequest(
        @NotNull(message = "Date is required")
        LocalDate date,

        @NotBlank(message = "State is required")
        String state,

        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Challenge id is required")
        Long challengeId
) {
}
