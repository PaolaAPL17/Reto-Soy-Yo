package com.reto.soyyo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "challenges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String type;

    private int duration;

    @Column(nullable = false, length = 20)
    private String level;

    @Column(length = 225)
    private String description;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Progress> progressList  = new HashSet<>();

    @ManyToMany(mappedBy = "challenges")
    private Set<User> users = new HashSet<>();
}
