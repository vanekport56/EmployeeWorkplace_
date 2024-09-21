package com.example.employeeworkplace.Services.UserServices;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Exceptions.CustomExceptions.EmailAlreadyExistsException;
import com.example.employeeworkplace.Exceptions.CustomExceptions.UsernameAlreadyExistsException;
import com.example.employeeworkplace.Mapper.UserMapper;
import com.example.employeeworkplace.Models.ConstantsAndEnums.Role;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import com.example.employeeworkplace.Security.Configuration.Secured;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * Сервис для управления пользователями и их аутентификации.
 * Предоставляет методы для получения, создания, редактирования и изменения данных пользователей.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Получает пользователя по его ID.
     *
     * @param userId идентификатор пользователя
     * @return объект User, если пользователь найден
     */
    public UserDTO getUserDTOById(Long userId) {
        log.debug("Запрос пользователя с ID: {}", userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            log.debug("Получен пользователь: {}", user);
            UserDTO userDTO = userMapper.userToUserDTO(user);
            log.debug("Преобразованный UserDTO: {}", userDTO);
            return userDTO;
        } else {
            log.debug("Пользователь с ID {} не найден", userId);
            return null;
        }
    }

    /**
     * Получает пользователя по его имени пользователя.
     *
     * @param username имя пользователя
     * @return объект User, если пользователь найден
     * @throws RuntimeException если пользователь не найден
     */
    public User getUserByUsername(String username) {
        log.debug("Поиск пользователя по имени пользователя: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь с именем пользователя не найден: " + username));
    }

    /**
     * Сохраняет нового пользователя в базе данных.
     * Выполняется проверка на уникальность имени пользователя и электронной почты.
     *
     * @param user объект нового пользователя
     * @throws UsernameAlreadyExistsException если имя пользователя уже существует
     * @throws EmailAlreadyExistsException    если электронная почта уже используется
     */
    public void saveNewUser(User user) {
        log.debug("Сохранение нового User: {}", user);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.error("Имя пользователя с ролью User уже существует: {}", user.getUsername());
            throw new UsernameAlreadyExistsException("Указанный логин занят: " + user.getUsername());
        }

        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("Электронная почта с ролью User уже используется: {}", user.getEmail());
            throw new EmailAlreadyExistsException("Электронная почта уже используется: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.user);

        userRepository.save(user);
        log.info("Новый User с именем {} был создан", user.getUsername());
    }
    public void saveNewAdminUser(User user) {
        log.debug("Сохранение нового Admin: {}", user);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.error("Имя пользователя с ролью Admin уже существует: {}", user.getUsername());
            throw new UsernameAlreadyExistsException("Указанный логин занят: " + user.getUsername());
        }

        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("Электронная почта с ролью Admin уже используется: {}", user.getEmail());
            throw new EmailAlreadyExistsException("Электронная почта с ролью Admin уже используется: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.admin);

        userRepository.save(user);
        log.info("Новый пользователь с именем {} был создан", user.getUsername());
    }

    /**
     * Загружает пользователя для аутентификации по его имени пользователя.
     *
     * @param username имя пользователя
     * @return объект UserDetails для аутентификации
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Загрузка пользователя по имени пользователя: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем пользователя не найден: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    /**
     * Проверяет, заполнен ли профиль пользователя полностью.
     *
     * @param username имя пользователя
     * @return true, если профиль неполный, иначе false
     */
    public boolean isProfileIncomplete(String username) {
        User user = getUserByUsername(username);
        return user.getFullName() == null || user.getFullName().isEmpty() ||
                user.getPosition() == null || user.getPosition().isEmpty() ||
                user.getGender() == null ||
                user.getBirthDate() == null ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty() ||
                user.getProfilePhoto() == null;
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param user объект пользователя с обновленными данными
     */
    public void editingUser(User user) {
        userRepository.save(user);
    }


    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект User, если пользователь найден, иначе null
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Меняет пароль пользователя, если текущий пароль совпадает.
     *
     * @param username        имя пользователя
     * @param currentPassword текущий пароль
     * @param newPassword     новый пароль
     * @return true, если пароль успешно изменён, иначе false
     */
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return false;
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }
    @Secured("admin")
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь с именем " + username + " не найден"));
        userRepository.delete(user);
        log.info("Пользователь с именем {} был удалён", username);
    }
}
