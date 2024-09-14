package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Models.Primary.VacationDocumentation;
import com.example.employeeworkplace.Repositories.Primary.VacationDocumentationRepository;
import com.example.employeeworkplace.Security.CustomUserDetails;
import com.example.employeeworkplace.Services.UserService;
import com.example.employeeworkplace.Services.VacationDocumentationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VacationDocumentationServiceTest {

    @Mock
    private VacationDocumentationRepository vacationDocumentationRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private VacationDocumentationService vacationDocumentationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentUserId() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = new CustomUserDetails(1L, "user", "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Long userId = vacationDocumentationService.getCurrentUserId();

        // Assert
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void testGetCurrentUserRole() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getAuthorities()).thenReturn(Collections.singletonList(() -> "ROLE_USER"));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String role = vacationDocumentationService.getCurrentUserRole();

        // Assert
        assertThat(role).isEqualTo("ROLE_USER");
    }

    @Test
    void testFilterByCurrentUser() {
        // Arrange
        Long currentUserId = 1L;
        VacationDocumentation doc1 = new VacationDocumentation();
        doc1.setUserId(currentUserId);
        VacationDocumentation doc2 = new VacationDocumentation();
        doc2.setUserId(2L);
        List<VacationDocumentation> documents = Arrays.asList(doc1, doc2);

        when(vacationDocumentationService.getCurrentUserId()).thenReturn(currentUserId);
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName("John Doe");
        when(userService.getUserDTOById(currentUserId)).thenReturn(userDTO);

        // Act
        List<VacationDocumentation> filteredDocuments = vacationDocumentationService.filterByCurrentUser(documents, currentUserId);

        // Assert
        assertThat(filteredDocuments).hasSize(1);
        assertThat(filteredDocuments.get(0).getUserId()).isEqualTo(currentUserId);
        assertThat(filteredDocuments.get(0).getUserFullName()).isEqualTo("John Doe");
    }

    @Test
    void testSave() {
        // Arrange
        VacationDocumentation doc = new VacationDocumentation();
        doc.setId(1L);
        when(vacationDocumentationRepository.save(any(VacationDocumentation.class))).thenReturn(doc);

        // Act
        VacationDocumentation savedDoc = vacationDocumentationService.save(doc);

        // Assert
        assertThat(savedDoc.getId()).isEqualTo(1L);
        verify(vacationDocumentationRepository, times(1)).save(doc);
    }

    @Test
    void testFindAllWithUserDetails() {
        // Arrange
        VacationDocumentation doc1 = new VacationDocumentation();
        VacationDocumentation doc2 = new VacationDocumentation();
        List<VacationDocumentation> documents = Arrays.asList(doc1, doc2);
        when(vacationDocumentationRepository.findAll()).thenReturn(documents);

        // Act
        List<VacationDocumentation> result = vacationDocumentationService.findAllWithUserDetails();

        // Assert
        assertThat(result).hasSize(2);
        verify(vacationDocumentationRepository, times(1)).findAll();
    }
}
