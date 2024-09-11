package com.example.employeeworkplace.Config;

import com.example.employeeworkplace.Models.Primary.FileEntity;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс для настройки Kafka продюсера.
 * <p>
 * Настраивает фабрику продюсеров и шаблон Kafka для отправки сообщений с использованием {@link FileEntity}.
 */
@Configuration
public class KafkaConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

    /**
     * Создает и настраивает фабрику продюсеров для отправки сообщений в Kafka.
     * <p>
     * Используются строковые ключи и сериализация объектов {@link FileEntity} в формате JSON.
     *
     * @return фабрика продюсеров для Kafka
     */
    @Bean
    public ProducerFactory<String, FileEntity> producerFactory() {
        logger.info("Настройка фабрики продюсеров для Kafka с сервером localhost:9092");

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // Отключаем заголовки с типом

        logger.debug("Конфигурация продюсера Kafka: {}", configProps);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Создает и настраивает шаблон Kafka для отправки сообщений с объектами {@link FileEntity}.
     *
     * @return шаблон Kafka для отправки сообщений
     */
    @Bean
    public KafkaTemplate<String, FileEntity> kafkaTemplate() {
        logger.info("Создание KafkaTemplate для отправки сообщений с FileEntity");
        return new KafkaTemplate<>(producerFactory());
    }
}
