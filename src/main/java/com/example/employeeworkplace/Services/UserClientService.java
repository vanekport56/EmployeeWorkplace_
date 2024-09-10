package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.Secondary.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Сервис для взаимодействия с внешним сервисом пользователей.
 * Выполняет запросы для получения данных о пользователях.
 */
@Service
public class UserClientService {

    private static final Logger logger = LoggerFactory.getLogger(UserClientService.class);

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    /**
     * Конструктор для инициализации клиента RestTemplate и URL сервиса пользователей.
     *
     * @param restTemplate    клиент для отправки HTTP-запросов
     * @param userServiceUrl  базовый URL для запросов к сервису пользователей
     */
    public UserClientService(RestTemplate restTemplate,
                             @Value("${user.service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    /**
     * Получает данные пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект пользователя, если найден
     */
    public User getUserById(Long id) {
        logger.info("Запрос данных пользователя с ID: {}", id);
        User user = restTemplate.getForObject(userServiceUrl + "/users/" + id, User.class);
        if (user != null) {
            logger.info("Пользователь с ID {} найден: {}", id, user.getUsername());
        } else {
            logger.warn("Пользователь с ID {} не найден", id);
        }
        return user;
    }

    /**
     * Получает данные пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return объект пользователя, если найден
     */
    public User getUserByUsername(String username) {
        logger.info("Запрос данных пользователя с именем: {}", username);
        User user = restTemplate.getForObject(userServiceUrl + "/users/username/" + username, User.class);
        if (user != null) {
            logger.info("Пользователь {} найден", username);
        } else {
            logger.warn("Пользователь с именем {} не найден", username);
        }
        return user;
    }

}
