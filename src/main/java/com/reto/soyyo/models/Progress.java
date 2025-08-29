package com.reto.soyyo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 20)
    private String state;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private  User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id",nullable = false)
    private Challenge challenges;
}
