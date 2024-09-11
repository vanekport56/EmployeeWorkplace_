package com.example.employeeworkplace.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки кэша в приложении.
 * Включает механизм кэширования с использованием {@link ConcurrentMapCacheManager}.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    /**
     * Создает и возвращает экземпляр {@link ConcurrentMapCacheManager} для управления кэшем.
     *
     * @return {@link ConcurrentMapCacheManager} для кэширования данных.
     */
    @Bean
    public ConcurrentMapCacheManager cacheManager() {
        logger.info("Инициализация CacheManager...");
        return new ConcurrentMapCacheManager();
    }
}
