package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.Primary.FileEntity;
import com.example.employeeworkplace.Repositories.Primary.FileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Сервис для потребления сообщений из Kafka, связанных с файлами.
 * Слушает сообщения из топика "file-topic", преобразует их из JSON в сущности {@link FileEntity}
 * и сохраняет в базу данных.
 */
@Service
public class FileConsumerService {

    private static final Logger log = LoggerFactory.getLogger(FileConsumerService.class);

    private final FileRepository fileRepository;
    private final ObjectMapper objectMapper;

    /**
     * Конструктор для создания экземпляра {@link FileConsumerService}.
     *
     * @param fileRepository репозиторий для работы с сущностями {@link FileEntity}
     * @param objectMapper объект для преобразования JSON в объекты Java
     */
    public FileConsumerService(FileRepository fileRepository, ObjectMapper objectMapper) {
        this.fileRepository = fileRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Метод-слушатель Kafka для обработки сообщений из топика "file-topic".
     * Получает сообщения в виде строки JSON, преобразует их в объекты {@link FileEntity},
     * и сохраняет их в базу данных через {@link FileRepository}.
     *
     * @param message строковое представление сообщения в формате JSON
     */
    @KafkaListener(topics = "file-topic", groupId = "file-group")
    public void listen(String message) {
        try {

            FileEntity fileEntity = objectMapper.readValue(message, FileEntity.class);
            log.info("Получено сообщение из Kafka: {}", fileEntity);

            fileRepository.save(fileEntity);
            log.debug("Файл {} успешно сохранен в базу данных", fileEntity.getFileName());
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения: {}", e.getMessage());
        }
    }
}
