package com.example.employeeworkplace.Config;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * Конфигурационный класс для настройки и запуска миграций Flyway.
 * <p>
 * Миграции выполняются автоматически при запуске приложения с использованием указанного источника данных.
 */
@Configuration
public class FlywayConfig {

    private static final Logger logger = LoggerFactory.getLogger(FlywayConfig.class);

    /**
     * Создает и настраивает экземпляр Flyway для миграций базы данных.
     *
     * @param primaryDataSource основной источник данных для миграций
     * @return настроенный и выполненный экземпляр Flyway
     */
    @Bean
    public Flyway flyway(DataSource primaryDataSource) {
        logger.info("Инициализация Flyway с источником данных: {}", primaryDataSource);

        Flyway flyway = Flyway.configure()
                .dataSource(primaryDataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        logger.info("Начало миграции базы данных");
        flyway.migrate();
        logger.info("Миграция завершена");

        return flyway;
    }
}
