package com.example.employeeworkplace.Services.UserServices;

import com.example.employeeworkplace.Models.Secondary.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Сервис для взаимодействия с внешним сервисом пользователей.
 * Выполняет запросы для получения данных о пользователях.
 */
@Slf4j
@Service
public class UserClientService {

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
        log.info("Запрос данных пользователя с ID: {}", id);
        User user = restTemplate.getForObject(userServiceUrl + "/users/" + id, User.class);
        if (user != null) {
            log.info("Пользователь с ID {} найден: {}", id, user.getUsername());
        } else {
            log.warn("Пользователь с ID {} не найден", id);
        }
        return user;
    }

}
