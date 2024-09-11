package com.example.employeeworkplace.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Конфигурационный класс для включения поддержки Redis в управлении HTTP сессиями.
 * <p>
 * Эта конфигурация автоматически использует Redis для хранения сессий, что повышает масштабируемость
 * и устойчивость веб-приложения при работе с распределенными сессиями.
 */
@Configuration
@EnableRedisHttpSession
public class RedisHttpSessionConfig {

    private static final Logger log = LoggerFactory.getLogger(RedisHttpSessionConfig.class);

    public RedisHttpSessionConfig() {
        log.info("Конфигурация Redis для хранения HTTP сессий активирована");
    }
}
