package com.reto.soyyo.services;
package com.reto.soyyo.services;

import com.reto.soyyo.dtos.progress.ProgressRequest;
import com.reto.soyyo.dtos.progress.ProgressResponse;
import com.reto.soyyo.models.Challenge;
import com.reto.soyyo.models.Progress;
import com.reto.soyyo.models.User;
import com.reto.soyyo.repositories.ChallengeRepository;
import com.reto.soyyo.repositories.ProgressRepository;
import com.reto.soyyo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    private User adminUser;
    private User normalUser;
    private Challenge challenge;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminUser = new User();
        adminUser.setId(1L);
        adminUser.setRole("ADMIN");
        adminUser.setEmail("admin@test.com");

        normalUser = new User();
        normalUser.setId(2L);
        normalUser.setRole("USER");
        normalUser.setEmail("user@test.com");

        challenge = new Challenge();
        challenge.setId(1L);
    }

    @Test
    void createProgress_adminCanCreateForAnyUser() {
        ProgressRequest request = new ProgressRequest(LocalDate.now(), "Completed", normalUser.getId(), challenge.getId());

        when(userRepository.findById(normalUser.getId())).thenReturn(Optional.of(normalUser));
        when(challengeRepository.findById(challenge.getId())).thenReturn(Optional.of(challenge));
        when(progressRepository.save(any(Progress.class))).thenAnswer(i -> i.getArgument(0));

        // Mock getAuthenticatedUser to return admin
        ProgressService spyService = spy(progressService);
        doReturn(adminUser).when(spyService).getAuthenticatedUser();

        ProgressResponse response = spyService.createProgress(request);

        assertNotNull(response);
        assertEquals(normalUser.getId(), response.userId());
    }

    @Test
    void createProgress_userCannotCreateForAnotherUser() {
        ProgressRequest request = new ProgressRequest(LocalDate.now(), "Completed", adminUser.getId(), challenge.getId());

        // Mock getAuthenticatedUser to return normalUser
        ProgressService spyService = spy(progressService);
        doReturn(normalUser).when(spyService).getAuthenticatedUser();

        SecurityException exception = assertThrows(SecurityException.class, () -> {
            spyService.createProgress(request);
        });

        assertEquals("Users can only create their own progress", exception.getMessage());
    }

    @Test
    void createProgress_userCanCreateForSelf() {
        ProgressRequest request = new ProgressRequest(LocalDate.now(), "Completed", normalUser.getId(), challenge.getId());

        when(userRepository.findById(normalUser.getId())).thenReturn(Optional.of(normalUser));
        when(challengeRepository.findById(challenge.getId())).thenReturn(Optional.of(challenge));
        when(progressRepository.save(any(Progress.class))).thenAnswer(i -> i.getArgument(0));

        ProgressService spyService = spy(progressService);
        doReturn(normalUser).when(spyService).getAuthenticatedUser();

        ProgressResponse response = spyService.createProgress(request);

        assertNotNull(response);
        assertEquals(normalUser.getId(), response.userId());
    }
}
