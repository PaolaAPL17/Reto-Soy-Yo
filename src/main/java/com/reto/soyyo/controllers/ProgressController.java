package com.reto.soyyo.controllers;

import com.reto.soyyo.models.Progress;
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
    public ResponseEntity<Progress> createProgress(@RequestBody Progress progress) {
        Progress createdProgress = progressService.saveProgress(progress);
        return new ResponseEntity<>(createdProgress, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Progress>> getAllProgress() {
        List<Progress> progressList = progressService.getAllProgress();
        return new ResponseEntity<>(progressList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progress> getProgressById(@PathVariable Long id) {
        return progressService.getProgressById(id)
                .map(progress -> new ResponseEntity<>(progress, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}