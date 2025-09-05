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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public ProgressResponse createProgress(ProgressRequest request) {
        User currentUser = getAuthenticatedUser();

        if (!currentUser.getRol().equals("ADMIN") && !currentUser.getId().equals(request.userId())) {
            throw new SecurityException("Users can only create their own progress");
        }

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

    public List<ProgressResponse> getProgressByUser(Long userId) {
        User currentUser = getAuthenticatedUser();

        if (!currentUser.getRol().equals("ADMIN") && !currentUser.getId().equals(userId)) {
            throw new SecurityException("Users can only view their own progress");
        }

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        return progressRepository.findByUser_Id(userId)
                .stream()
                .map(ProgressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
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

    @Transactional
    public void deleteProgress(Long id) {
        if (!progressRepository.existsById(id)) {
            throw new EntityNotFoundException("Progress not found with id: " + id);
        }
        progressRepository.deleteById(id);
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("No authenticated user found");
        }
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found"));
    }
}
