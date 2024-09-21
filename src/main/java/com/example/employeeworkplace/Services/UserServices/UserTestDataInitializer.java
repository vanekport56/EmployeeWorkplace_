package com.example.employeeworkplace.Services.UserServices;

import com.example.employeeworkplace.Models.ConstantsAndEnums.Gender;
import com.example.employeeworkplace.Models.ConstantsAndEnums.Role;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service

public class UserTestDataInitializer {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserTestDataInitializer(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        log.info("Инициализация пользователей...");
        createUserIfNotExists("admin", Role.admin, "Петя", "admin@testik.com", "Мужчина");
        createUserIfNotExists("user", Role.user, "Катя", "user@testik.com", "Женщина");
        log.info("Инициализация завершена.");
    }

    private void createUserIfNotExists(String username, Role role, String fullName, String email, String gender) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            log.info("Создание пользователя: {}", username);
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode("1"));
            user.setRole(role);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setGender("Мужчина".equals(gender) ? Gender.MALE : Gender.FEMALE);
            userRepository.save(user);
            log.info("Пользователь {} успешно создан.", username);
        } else {
            log.info("Пользователь {} уже существует, пропуск создания.", username);
        }
    }
}