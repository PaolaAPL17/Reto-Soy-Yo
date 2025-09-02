package com.reto.soyyo.dtos.progress;

import com.reto.soyyo.models.Progress;
import com.reto.soyyo.models.User;
import com.reto.soyyo.models.Challenge;

public class ProgressMapper {

    public static Progress toEntity(ProgressRequest request, User user, Challenge challenge) {
        Progress progress = new Progress();
        progress.setDate(request.date());
        progress.setState(request.state());
        progress.setUser(user);
        progress.setChallenge(challenge);
        return progress;
    }

    public static ProgressResponse toDto(Progress progress) {
        return new ProgressResponse(
                progress.getId(),
                progress.getDate(),
                progress.getState(),
                progress.getUser().getId(),
                progress.getChallenge().getId()
        );
    }
}
