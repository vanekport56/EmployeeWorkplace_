package com.example.employeeworkplace.Security.Authentication;

import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Реализация UserDetailsService для загрузки данных пользователя.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Конструктор для инъекции зависимости.
     *
     * @param userRepository Репозиторий пользователей.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загружает данные пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Объект UserDetails.
     * @throws UsernameNotFoundException Если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return new CustomUserDetails(user);
    }
}
