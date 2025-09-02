package com.reto.soyyo.dtos.challenge;

import com.reto.soyyo.models.Challenge;

public class ChallengeMapper {

    public static Challenge toEntity(ChallengeRequest request) {
        Challenge challenge = new Challenge();
        challenge.setTitle(request.title());
        challenge.setDescription(request.description());
        return challenge;
    }

    public static ChallengeResponse toDto(Challenge challenge) {
        return new ChallengeResponse(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getDescription()
        );
    }
}
