package com.reto.soyyo.services;

import com.reto.soyyo.dtos.progress.ProgressRequest;
import com.reto.soyyo.dtos.progress.ProgressResponse;
import com.reto.soyyo.dtos.progress.ProgressMapper;
import com.reto.soyyo.models.Challenge;
import com.reto.soyyo.models.Progress;
import com.reto.soyyo.models.User;
import com.reto.soyyo.repositories.ChallengeRepository;
import com.reto.soyyo.repositories.ProgressRepository;
import com.reto.soyyo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    public ProgressResponse createProgress(ProgressRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.userId()));
        Challenge challenge = challengeRepository.findById(request.challengeId())
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id: " + request.challengeId()));

        Progress progress = ProgressMapper.toEntity(request, user, challenge);
        return ProgressMapper.toDto(progressRepository.save(progress));
    }

    public List<ProgressResponse> getAllProgress() {
        return progressRepository.findAll()
                .stream()
                .map(ProgressMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProgressResponse getProgressById(Long id) {
        return progressRepository.findById(id)
                .map(ProgressMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Progress not found with id: " + id));
    }

    public ProgressResponse updateProgress(Long id, ProgressRequest request) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Progress not found with id: " + id));

        progress.setDate(request.date());
        progress.setState(request.state());

        if (request.userId() != null) {
            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.userId()));
            progress.setUser(user);
        }

        if (request.challengeId() != null) {
            Challenge challenge = challengeRepository.findById(request.challengeId())
                    .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id: " + request.challengeId()));
            progress.setChallenge(challenge);
        }

        return ProgressMapper.toDto(progressRepository.save(progress));
    }

    public void deleteProgress(Long id) {
        if (!progressRepository.existsById(id)) {
            throw new EntityNotFoundException("Progress not found with id: " + id);
        }
        progressRepository.deleteById(id);
    }
}
