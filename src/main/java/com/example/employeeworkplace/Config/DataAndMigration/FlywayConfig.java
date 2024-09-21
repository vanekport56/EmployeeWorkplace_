package com.example.employeeworkplace.Config.DataAndMigration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * Конфигурационный класс для настройки и запуска миграций Flyway.
 * <p>
 * Миграции выполняются автоматически при запуске приложения с использованием указанного источника данных.
 */
@Slf4j
@Configuration
public class FlywayConfig {



    /**
     * Создает и настраивает экземпляр Flyway для миграций базы данных.
     *
     * @param primaryDataSource основной источник данных для миграций
     * @return настроенный и выполненный экземпляр Flyway
     */
    @Bean
    public Flyway flyway(DataSource primaryDataSource) {
        log.info("Инициализация Flyway с источником данных: {}", primaryDataSource);

        Flyway flyway = Flyway.configure()
                .dataSource(primaryDataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        log.info("Начало миграции базы данных");
        flyway.migrate();
        log.info("Миграция завершена");

        return flyway;
    }
}
