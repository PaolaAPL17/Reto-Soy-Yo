package com.reto.soyyo.services;

import com.reto.soyyo.models.Challenge;
import com.reto.soyyo.repositories.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public Challenge saveChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Optional<Challenge> getChallengeById(Long id){
        return challengeRepository.findById(id);
    }

    public Optional<Challenge> updateChallenge(Long id, Challenge challengeDetails) {
        return challengeRepository.findById(id)
                .map(challenge -> {
                    challenge.setTitle(challengeDetails.getTitle());
                    challenge.setDescription(challengeDetails.getDescription());
                    return challengeRepository.save(challenge);
                });
    }

    public void deleteChallenge(Long id) {
        challengeRepository.deleteById(id);
    }
}
