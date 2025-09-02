package com.reto.soyyo.dtos.progress;

import java.time.LocalDate;

public record ProgressResponse(
        Long id,
        LocalDate date,
        String state,
        Long userId,
        Long challengeId
) {
}
