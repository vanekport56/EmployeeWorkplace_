package com.example.employeeworkplace.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Конфигурация вторичного источника данных и JPA.
 * <p>
 * Настраивает подключение к вторичной базе данных и конфигурацию JPA для управления сущностями.
 * </p>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.employeeworkplace.Repositories.Secondary",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(SecondaryDataSourceConfig.class);

    /**
     * Настраивает источник данных для вторичной базы данных PostgreSQL.
     * <p>
     * Использует {@link DriverManagerDataSource} с указанными параметрами подключения.
     * </p>
     *
     * @return Экземпляр {@link DataSource}
     */
    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource() {
        log.debug("Создание вторичного источника данных");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/U");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");

        return dataSource;
    }

    /**
     * Настраивает фабрику {@link LocalContainerEntityManagerFactoryBean} для вторичной базы данных.
     * <p>
     * Конфигурирует {@link LocalContainerEntityManagerFactoryBean} для работы с Hibernate.
     * </p>
     *
     * @param dataSource Источник данных для фабрики
     * @return Настроенный экземпляр {@link LocalContainerEntityManagerFactoryBean}
     */
    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            @Qualifier("secondaryDataSource") DataSource dataSource) {
        log.debug("Создание вторичной фабрики EntityManager");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.example.employeeworkplace.Models.Secondary");
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
    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        log.debug("Создание вторичного менеджера транзакций");

        if (entityManagerFactory.getObject() == null) {
            throw new IllegalStateException("EntityManagerFactory не инициализирован");
        }

        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
