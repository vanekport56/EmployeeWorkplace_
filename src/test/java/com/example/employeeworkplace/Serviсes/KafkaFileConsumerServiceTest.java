package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.FileEntity;
import com.example.employeeworkplace.Repositories.Primary.FileRepository;
import com.example.employeeworkplace.Services.FileConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Тестовый класс для проверки интеграции с Kafka через Docker.
 * <p>
 * Этот тест проверяет, что сообщения, отправленные в Kafka-топик, корректно обрабатываются и сохраняются
 * в базе данных. Для этого тест выполняет следующие шаги:
 * <ol>
 *     <li>Очистка репозитория файлов перед каждым тестом.</li>
 *     <li>Создание тестового сообщения в формате JSON и отправка его в Kafka-топик через Docker.</li>
 *     <li>Ожидание, пока сообщение будет обработано и сохранено в базе данных.</li>
 *     <li>Проверка, что сообщение сохранено в базе данных и наличие файла с ожидаемыми значениями.</li>
 * </ol>
 */
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class KafkaFileConsumerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaFileConsumerServiceTest.class);

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileConsumerService fileConsumerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        logger.debug("Настройка теста: очистка репозитория файлов");
        fileRepository.deleteAll();

        logger.debug("Регистрация модуля JavaTimeModule для ObjectMapper");
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Test
    public void testKafkaConsumer() throws Exception {
        logger.debug("Запуск теста потребителя Kafka через Docker");

        String jsonMessage = "{\"fileName\": \"example.txt\", \"filePath\": \"/path/to/file\", \"fileSize\": 1234, \"uploadTime\": \"2024-09-14T15:30:00\"}";
        logger.debug("Сообщение для отправки в Kafka-топик: {}", jsonMessage);

        sendMessageToKafkaViaDocker(jsonMessage);

        logger.debug("Ожидание, пока сообщение будет обработано и сохранено в базу данных...");
        await().atMost(60, SECONDS).until(() -> {
            logger.debug("Проверка наличия файла 'example.txt' в репозитории...");
            List<FileEntity> savedFileEntities = fileRepository.findByFileName("example.txt");
            boolean fileExists = !savedFileEntities.isEmpty();
            logger.debug("Файл найден в репозитории: {}", fileExists);
            return fileExists;
        });

        logger.debug("Проверка, что сообщение сохранено в базе данных");
        List<FileEntity> savedFileEntities = fileRepository.findByFileName("example.txt");
        if (!savedFileEntities.isEmpty()) {
            logger.debug("Файл(ы) успешно найден(ы) в базе данных: {}", savedFileEntities);
            assertThat(savedFileEntities).isNotEmpty();  // проверка, что список не пуст
            // Дополнительные проверки, если нужно
            for (FileEntity fileEntity : savedFileEntities) {
                assertThat(fileEntity.getFileName()).isEqualTo("example.txt");
                assertThat(fileEntity.getFilePath()).isEqualTo("/path/to/file");
                assertThat(fileEntity.getFileSize()).isEqualTo(1234L);
            }
        } else {
            logger.error("Файл 'example.txt' не найден в базе данных");
        }
    }

    private void sendMessageToKafkaViaDocker(String jsonMessage) {
        try {

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "exec", "-i", "infrastructure-kafka-1",
                    "kafka-console-producer", "--broker-list", "localhost:9092", "--topic", "file-topic"
            );


            Process process = processBuilder.start();

            try (OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream())) {
                writer.write(jsonMessage + "\n");
                writer.flush();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.debug("Kafka console output: " + line);
            }


            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.debug("Сообщение успешно отправлено в Kafka через консоль.");
            } else {
                logger.error("Ошибка при отправке сообщения через Kafka консоль. Код выхода: " + exitCode);
            }

        } catch (Exception e) {
            logger.error("Произошла ошибка при отправке сообщения в Kafka через консоль: {}", e.getMessage());
        }
    }
}
