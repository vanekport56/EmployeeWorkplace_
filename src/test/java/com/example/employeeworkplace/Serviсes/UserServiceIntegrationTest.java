package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Secondary.Role;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import com.example.employeeworkplace.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testSaveAndLoadUser() {
        User user = new User();
        user.setUsername("integrationuser");
        user.setEmail("integrationuser@example.com");
        user.setPassword("password");
        user.setRole(Role.user);

        userService.saveNewUser(user);

        User loadedUser = userRepository.findByUsername("integrationuser").orElse(null);

        assertThat(loadedUser).isNotNull();
        assertThat(passwordEncoder.matches("password", loadedUser.getPassword())).isTrue();
        assertThat(loadedUser.getRole()).isEqualTo(Role.user);
    }

    @Test
    public void testSaveAndLoadAdmin() {
        User admin = new User();
        admin.setUsername("adminuser");
        admin.setEmail("adminuser@example.com");
        admin.setPassword("adminpassword");

        userService.saveNewAdminUser(admin);

        User loadedAdmin = userRepository.findByUsername("adminuser").orElse(null);

        assertThat(loadedAdmin).isNotNull();
        assertThat(passwordEncoder.matches("adminpassword", loadedAdmin.getPassword())).isTrue();
        assertThat(loadedAdmin.getRole()).isEqualTo(Role.admin);
    }

    @Test
    public void testChangePassword() {
        User user = new User();
        user.setUsername("passworduser");
        user.setEmail("passworduser@example.com");
        user.setPassword(passwordEncoder.encode("oldPassword"));
        user.setRole(Role.user);
        userRepository.save(user);

        boolean result = userService.changePassword("passworduser", "oldPassword", "newPassword");

        assertThat(result).isTrue();
        User updatedUser = userRepository.findByUsername("passworduser").orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(passwordEncoder.matches("newPassword", updatedUser.getPassword())).isTrue();
    }
}
