package com.example.employeeworkplace.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Конфигурация для настроек приложения.
 * <p>
 * Включает настройки для создания бина {@link RestTemplate} с поддержкой обработки JSON и
 * активирует поддержку аспектов через AspectJ.
 * </p>
 */
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    /**
     * Создаёт бин {@link RestTemplate} с конфигурацией для обработки JSON.
     * <p>
     * В конфигурацию включён {@link MappingJackson2HttpMessageConverter} для обработки JSON.
     * </p>
     *
     * @return Экземпляр {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }
}
