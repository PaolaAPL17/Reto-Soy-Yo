package com.reto.soyyo.services;

import com.reto.soyyo.dtos.challenge.ChallengeRequest;
import com.reto.soyyo.dtos.challenge.ChallengeResponse;
import com.reto.soyyo.dtos.challenge.ChallengeMapper;
import com.reto.soyyo.models.Challenge;
import com.reto.soyyo.repositories.ChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeResponse createChallenge(ChallengeRequest request) {
        Challenge challenge = ChallengeMapper.toEntity(request);
        return ChallengeMapper.toDto(challengeRepository.save(challenge));
    }

    public List<ChallengeResponse> getAllChallenges() {
        return challengeRepository.findAll()
                .stream()
                .map(ChallengeMapper::toDto)
                .collect(Collectors.toList());
    }

    public ChallengeResponse getChallengeById(Long id) {
        return challengeRepository.findById(id)
                .map(ChallengeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id: " + id));
    }

    public ChallengeResponse updateChallenge(Long id, ChallengeRequest request) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id: " + id));

        challenge.setTitle(request.title());
        challenge.setDescription(request.description());

        return ChallengeMapper.toDto(challengeRepository.save(challenge));
    }

    public void deleteChallenge(Long id) {
        if (!challengeRepository.existsById(id)) {
            throw new EntityNotFoundException("Challenge not found with id: " + id);
        }
        challengeRepository.deleteById(id);
    }
}