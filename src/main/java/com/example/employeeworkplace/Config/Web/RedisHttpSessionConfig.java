package com.example.employeeworkplace.Config.Web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Конфигурационный класс для включения поддержки Redis в управлении HTTP сессиями.
 * <p>
 * Эта конфигурация автоматически использует Redis для хранения сессий, что повышает масштабируемость
 * и устойчивость веб-приложения при работе с распределенными сессиями.
 */
@Slf4j
@Configuration
@EnableRedisHttpSession
public class RedisHttpSessionConfig {


    public RedisHttpSessionConfig() {
        log.info("Конфигурация Redis для хранения HTTP сессий активирована");
    }
}
