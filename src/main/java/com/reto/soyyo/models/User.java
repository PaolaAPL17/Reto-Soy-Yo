package com.reto.soyyo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.mapping.Set;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "username is required")
    @Size(min = 3, max = 50,message = "The username must be between 3 and 50 characters")
    private String username;

    @Column(unique = true,nullable = false, length = 100)
    @NotBlank(message = "The email is required")
    private String email;


    @Column(nullable = false)
    @NotBlank(message = "The password is required")
    @Size(min = 60,max = 100)
    private String password;

    @OneToMany(mappedBy = "user, cascade = CascadeType.ALL, orphanRemoval = True")
    private Set progressList = new HashSet<>();
}
