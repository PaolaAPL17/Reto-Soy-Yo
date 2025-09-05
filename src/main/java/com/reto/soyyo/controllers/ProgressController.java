package com.reto.soyyo.controllers;

import com.reto.soyyo.dtos.progress.ProgressRequest;
import com.reto.soyyo.dtos.progress.ProgressResponse;
import com.reto.soyyo.services.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProgressResponse> createProgress(@Valid @RequestBody ProgressRequest request) {
        return new ResponseEntity<>(progressService.createProgress(request), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProgressResponse>> getAllProgress() {
        return new ResponseEntity<>(progressService.getAllProgress(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProgressResponse> getProgressById(@PathVariable Long id) {
        return new ResponseEntity<>(progressService.getProgressById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ProgressResponse>> getProgressByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(progressService.getProgressByUser(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProgressResponse> updateProgress(@PathVariable Long id,
                                                           @Valid @RequestBody ProgressRequest request) {
        return new ResponseEntity<>(progressService.updateProgress(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
