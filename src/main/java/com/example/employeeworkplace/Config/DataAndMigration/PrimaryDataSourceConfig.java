package com.example.employeeworkplace.Config.DataAndMigration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
 * Конфигурационный класс для настройки первичного источника данных, EntityManagerFactory
 * и менеджера транзакций для работы с основной базой данных.
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.employeeworkplace.Repositories.Primary",
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDataSourceConfig {

    @Value("${spring.datasource.primary.url}")
    private String url;

    @Value("${spring.datasource.primary.username}")
    private String username;

    @Value("${spring.datasource.primary.password}")
    private String password;

    @Value("${spring.datasource.primary.driver-class-name}")
    private String driverClassName;

    /**
     * Создает и настраивает первичный источник данных для подключения к основной базе данных.
     *
     * @return источник данных для первичной базы данных
     */
    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        log.debug("Создание первичного источника данных с URL: {}", url);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Создает и настраивает фабрику EntityManager для работы с сущностями основной базы данных.
     *
     * @param dataSource источник данных, который будет использоваться фабрикой EntityManager
     * @return фабрика EntityManager для первичной базы данных
     */
    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        log.debug("Создание первичной фабрики EntityManager для пакета com.example.employeeworkplace.Models.Primary");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.example.employeeworkplace.Models.Primary");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        entityManager.setJpaProperties(properties);

        log.debug("Настройки Hibernate: {}", properties);

        return entityManager;
    }

    /**
     * Создает и настраивает менеджер транзакций для работы с основной базой данных.
     *
     * @param entityManagerFactory фабрика EntityManager, с которой будет работать менеджер транзакций
     * @return менеджер транзакций для первичной базы данных
     */
    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        log.debug("Создание первичного менеджера транзакций");

        if (entityManagerFactory.getObject() == null) {
            log.error("EntityManagerFactory не инициализирован");
            throw new IllegalStateException("EntityManagerFactory не инициализирован");
        }

        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
