package com.reto.soyyo.services;

import com.reto.soyyo.dtos.progress.ProgressRequest;
import com.reto.soyyo.dtos.progress.ProgressResponse;
import com.reto.soyyo.models.Challenge;
import com.reto.soyyo.models.Progress;
import com.reto.soyyo.models.Role;
import com.reto.soyyo.models.User;
import com.reto.soyyo.repositories.ChallengeRepository;
import com.reto.soyyo.repositories.ProgressRepository;
import com.reto.soyyo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProgressServiceTest {

    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private ProgressService progressService;

    private User normalUser;
    private Challenge challenge;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        normalUser = new User();
        normalUser.setId(1L);
        normalUser.setEmail("user@mail.com");
        normalUser.setRole(Role.USER);

        challenge = new Challenge();
        challenge.setId(1L);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(normalUser.getEmail())
                .password("password")
                .roles("USER")
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void createProgress_userCanCreateForSelf() {
        ProgressRequest request = new ProgressRequest(
                LocalDate.now(),
                "IN_PROGRESS",
                normalUser.getId(),
                challenge.getId()
        );

        when(userRepository.findById(normalUser.getId())).thenReturn(Optional.of(normalUser));
        when(challengeRepository.findById(challenge.getId())).thenReturn(Optional.of(challenge));
        when(progressRepository.save(any(Progress.class))).thenAnswer(inv -> inv.getArgument(0));

        ProgressResponse response = progressService.createProgress(request);

        assertNotNull(response);
        verify(progressRepository, times(1)).save(any(Progress.class));
    }
}
