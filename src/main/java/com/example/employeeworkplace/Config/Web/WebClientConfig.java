package com.example.employeeworkplace.Config.Web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Конфигурация для создания {@link WebClient}.
 * <p>
 * Этот класс настраивает {@link WebClient.Builder}, который может быть использован для создания экземпляров {@link WebClient}.
 * </p>
 */
@Configuration
public class WebClientConfig {

    /**
     * Создает и настраивает {@link WebClient.Builder}.
     * <p>
     * Предоставляет бин {@link WebClient.Builder}, который можно использовать для создания и настройки {@link WebClient}.
     * </p>
     *
     * @return Настроенный {@link WebClient.Builder}
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
