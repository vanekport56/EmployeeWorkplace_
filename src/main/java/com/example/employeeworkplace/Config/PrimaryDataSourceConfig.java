package com.example.employeeworkplace.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурация источника данных и JPA.
 * <p>
 * Настраивает подключение к базе данных и конфигурацию JPA для управления сущностями.
 * </p>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.employeeworkplace.Repositories.Primary",
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfig {
    private static final Logger log = LoggerFactory.getLogger(PrimaryDataSourceConfig.class);

    /**
     * Настраивает источник данных для подключения к базе данных PostgreSQL.
     * <p>
     * Использует {@link DriverManagerDataSource} с указанными параметрами подключения.
     * </p>
     *
     * @return Экземпляр {@link DataSource}
     */
    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        log.debug("Создание первичного источника данных");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/D");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        return dataSource;
    }

    /**
     * Настраивает фабрику {@link LocalContainerEntityManagerFactoryBean}.
     * <p>
     * Конфигурирует {@link LocalContainerEntityManagerFactoryBean} для работы с Hibernate.
     * </p>
     *
     * @param dataSource Источник данных для фабрики
     * @return Настроенный экземпляр {@link LocalContainerEntityManagerFactoryBean}
     */
    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        log.debug("Создание первичной фабрики EntityManager");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.example.employeeworkplace.Models.Primary");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        entityManager.setJpaProperties(properties);

        return entityManager;
    }

    /**
     * Настраивает менеджер транзакций {@link JpaTransactionManager}.
     * <p>
     * Использует {@link LocalContainerEntityManagerFactoryBean} для управления транзакциями.
     * </p>
     *
     * @param entityManagerFactory Фабрика EntityManager для управления транзакциями
     * @return Экземпляр {@link PlatformTransactionManager}
     * @throws IllegalStateException Если фабрика {@link LocalContainerEntityManagerFactoryBean} не инициализирована
     */
    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        log.debug("Создание первичного менеджера транзакций");

        if (entityManagerFactory.getObject() == null) {
            throw new IllegalStateException("EntityManagerFactory не инициализирован");
        }

        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
