package com.reto.soyyo.dtos.challenge;

import jakarta.validation.constraints.NotNull;

public record AssignChallengeRequest(
        @NotNull Long challengeId
){}
