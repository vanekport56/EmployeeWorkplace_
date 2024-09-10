package com.example.employeeworkplace.Repositories.Secondary;

import com.example.employeeworkplace.Models.Secondary.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с {@link User}.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты
     * @return {@link Optional} пользователя
     */
    Optional<User> findByEmail(String email);

    /**
     * Находит пользователя по имени.
     *
     * @param username имя пользователя
     * @return {@link Optional} пользователя
     */
    Optional<User> findByUsername(String username);

}
