package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.Secondary.Gender;
import com.example.employeeworkplace.Models.Secondary.Role;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class TESTuserSetUp {

    private static final Logger logger = LoggerFactory.getLogger(TESTuserSetUp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        logger.info("Инициализация пользователей...");
        createUserIfNotExists("admin", "1", Role.admin, "Петя", "admin@testik.com", "Мужчина");
        createUserIfNotExists("user", "1", Role.user, "Катя", "user@testik.com", "Женщина");
        logger.info("Инициализация завершена.");
    }

    private void createUserIfNotExists(String username, String password, Role role, String fullName, String email, String gender) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            logger.info("Создание пользователя: {}", username);
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setGender("Мужчина".equals(gender) ? Gender.MALE : Gender.FEMALE);
            userRepository.save(user);
            logger.info("Пользователь {} успешно создан.", username);
        } else {
            logger.info("Пользователь {} уже существует, пропуск создания.", username);
        }
    }
}