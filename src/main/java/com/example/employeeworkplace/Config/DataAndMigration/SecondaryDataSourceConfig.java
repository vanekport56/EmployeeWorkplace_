package com.example.employeeworkplace.Config.DataAndMigration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * Конфигурационный класс для настройки вторичного источника данных, EntityManagerFactory
 * и менеджера транзакций для работы с вторичной базой данных.
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.employeeworkplace.Repositories.Secondary",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfig {


    @Value("${spring.datasource.secondary.url}")
    private String url;

    @Value("${spring.datasource.secondary.username}")
    private String username;

    @Value("${spring.datasource.secondary.password}")
    private String password;

    @Value("${spring.datasource.secondary.driver-class-name}")
    private String driverClassName;

    /**
     * Создает и настраивает вторичный источник данных для подключения к дополнительной базе данных.
     *
     * @return источник данных для вторичной базы данных
     */
    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource() {
        log.debug("Создание вторичного источника данных с URL: {}", url);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Создает и настраивает фабрику EntityManager для работы с сущностями вторичной базы данных.
     *
     * @param dataSource источник данных, который будет использоваться фабрикой EntityManager
     * @return фабрика EntityManager для вторичной базы данных
     */
    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            @Qualifier("secondaryDataSource") DataSource dataSource) {
        log.debug("Создание вторичной фабрики EntityManager для пакета com.example.employeeworkplace.Models.Secondary");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.example.employeeworkplace.Models.Secondary");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        entityManager.setJpaProperties(properties);

        log.debug("Настройки Hibernate для вторичной базы данных: {}", properties);

        return entityManager;
    }

    /**
     * Создает и настраивает менеджер транзакций для работы с вторичной базой данных.
     *
     * @param entityManagerFactory фабрика EntityManager, с которой будет работать менеджер транзакций
     * @return менеджер транзакций для вторичной базы данных
     */
    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        log.debug("Создание вторичного менеджера транзакций");

        if (entityManagerFactory.getObject() == null) {
            log.error("EntityManagerFactory не инициализирован");
            throw new IllegalStateException("EntityManagerFactory не инициализирован");
        }

        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
