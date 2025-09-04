package com.reto.soyyo.controllers;

import com.reto.soyyo.dtos.challenge.ChallengeRequest;
import com.reto.soyyo.dtos.challenge.ChallengeResponse;
import com.reto.soyyo.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChallengeResponse> createChallenge(@RequestBody ChallengeRequest request) {
        return new ResponseEntity<>(challengeService.createChallenge(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> getAllChallenges() {
        return new ResponseEntity<>(challengeService.getAllChallenges(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponse> getChallengeById(@PathVariable Long id) {
        return new ResponseEntity<>(challengeService.getChallengeById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChallengeResponse> updateChallenge(@PathVariable Long id,@RequestBody ChallengeRequest request) {
        return new ResponseEntity<>(challengeService.updateChallenge(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}