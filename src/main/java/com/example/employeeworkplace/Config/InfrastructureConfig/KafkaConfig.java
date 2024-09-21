package com.example.employeeworkplace.Config.InfrastructureConfig;

import com.example.employeeworkplace.Models.Primary.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
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
@Slf4j
@Configuration
public class KafkaConfig {

  

    /**
     * Создает и настраивает фабрику продюсеров для отправки сообщений в Kafka.
     * <p>
     * Используются строковые ключи и сериализация объектов {@link FileEntity} в формате JSON.
     *
     * @return фабрика продюсеров для Kafka
     */
    @Bean
    public ProducerFactory<String, FileEntity> producerFactory() {
        log.info("Настройка фабрики продюсеров для Kafka с сервером localhost:9092");

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // Отключаем заголовки с типом

        log.debug("Конфигурация продюсера Kafka: {}", configProps);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Создает и настраивает шаблон Kafka для отправки сообщений с объектами {@link FileEntity}.
     *
     * @return шаблон Kafka для отправки сообщений
     */
    @Bean
    public KafkaTemplate<String, FileEntity> kafkaTemplate() {
        log.info("Создание KafkaTemplate для отправки сообщений с FileEntity");
        return new KafkaTemplate<>(producerFactory());
    }
}
