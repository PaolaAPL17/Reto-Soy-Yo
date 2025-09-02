package com.reto.soyyo.controllers;

import com.reto.soyyo.dtos.progress.ProgressRequest;
import com.reto.soyyo.dtos.progress.ProgressResponse;
import com.reto.soyyo.services.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping
    public ResponseEntity<ProgressResponse> createProgress(@RequestBody ProgressRequest request) {
        return new ResponseEntity<>(progressService.createProgress(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProgressResponse>> getAllProgress() {
        return new ResponseEntity<>(progressService.getAllProgress(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressResponse> getProgressById(@PathVariable Long id) {
        return new ResponseEntity<>(progressService.getProgressById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressResponse> updateProgress(@PathVariable Long id,
                                                           @RequestBody ProgressRequest request) {
        return new ResponseEntity<>(progressService.updateProgress(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}