package com.reto.soyyo.repositories;

import com.reto.soyyo.models.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUser_Id(Long userId);
    List<Progress> findByChallenge_Id(Long challengeId);
}
