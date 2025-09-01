package com.reto.soyyo.services;

import com.reto.soyyo.models.Progress;
import com.reto.soyyo.repositories.ChallengeRepository;
import com.reto.soyyo.repositories.ProgressRepository;
import com.reto.soyyo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    public Progress saveProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    public List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }

    public Optional<Progress> getProgressById(Long id) {
        return progressRepository.findById(id);
    }

    public Optional<Progress> updateProgress(Long id, Progress progressDetails) {
        return progressRepository.findById(id)
                .map(progress -> {
                    progress.setDate(progressDetails.getDate());
                    progress.setState(progressDetails.getState());

                    if (progressDetails.getUser() != null) {
                        userRepository.findById(progressDetails.getUser().getId())
                                .ifPresent(progress::setUser);
                    }
                    if (progressDetails.getChallenge() != null) {
                        challengeRepository.findById(progressDetails.getChallenge().getId())
                                .ifPresent(progress::setChallenge);
                    }

                    return progressRepository.save(progress);
                });
    }

    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }
}
