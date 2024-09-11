package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.Primary.FileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для отправки сообщений в Kafka, связанных с файлами.
 * Использует {@link KafkaTemplate} для публикации сущностей {@link FileEntity} в топик "file-topic".
 */
@Service
public class FileProducerService {

    private static final Logger log = LoggerFactory.getLogger(FileProducerService.class);

    private final KafkaTemplate<String, FileEntity> kafkaTemplate;

    public FileProducerService(KafkaTemplate<String, FileEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Отправляет сущность {@link FileEntity} в Kafka-топик "file-topic".
     *
     * @param fileEntity сущность файла, которая будет отправлена
     */
    public void sendFileEntity(FileEntity fileEntity) {
        log.info("Отправка сущности файла в Kafka: {}", fileEntity);
        try {
            kafkaTemplate.send("file-topic", fileEntity);
            log.debug("Сущность файла {} успешно отправлена в топик 'file-topic'", fileEntity.getFileName());
        } catch (Exception e) {
            log.error("Ошибка при отправке сущности файла {} в Kafka: {}", fileEntity.getFileName(), e.getMessage());
        }
    }
}
