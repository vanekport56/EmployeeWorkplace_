package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Services.DocumentNumberGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интеграционные тесты для сервиса генерации номеров документов {@link com.example.employeeworkplace.Services.DocumentNumberGeneratorService}.
 * Проверяют функциональность генерации и сброса счетчика номеров документов.
 */
@SpringBootTest
public class DocumentNumberGeneratorServiceIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(DocumentNumberGeneratorServiceIntegrationTest.class);

    @Autowired
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    /**
     * Устанавливает начальное состояние перед каждым тестом.
     * Сбрасывает счетчик генератора номеров документов.
     */
    @BeforeEach
    void setUp() {
        documentNumberGeneratorService.resetCounter();
        logger.debug("Счетчик номера документа сброшен.");
    }

    /**
     * Тестирует генерацию номера документа.
     * Проверяет, что номера документов генерируются в правильном формате и являются уникальными.
     */
    @Test
    public void testGenerateDocumentNumber() {
        String documentType = "INV";

        String number1 = documentNumberGeneratorService.generateDocumentNumber(documentType);
        String number2 = documentNumberGeneratorService.generateDocumentNumber(documentType);

        logger.debug("Сгенерированный номер документа 1: {}", number1);
        logger.debug("Сгенерированный номер документа 2: {}", number2);

        assertThat(number1).matches(String.format("%s-%d-\\d{3}", documentType, Year.now().getValue()));
        assertThat(number2).matches(String.format("%s-%d-\\d{3}", documentType, Year.now().getValue()));

        assertThat(number1).isNotEqualTo(number2);
    }

    /**
     * Тестирует сброс счетчика.
     * Проверяет, что после сброса счетчика номера документов начинаются заново.
     */
    @Test
    public void testResetCounter() {
        String documentType = "REP";

        String number1 = documentNumberGeneratorService.generateDocumentNumber(documentType);
        documentNumberGeneratorService.resetCounter();
        String number2 = documentNumberGeneratorService.generateDocumentNumber(documentType);

        logger.debug("Сгенерированный номер документа до сброса: {}", number1);
        logger.debug("Сгенерированный номер документа после сброса: {}", number2);

        // Поскольку сброс счетчика, первый номер после сброса должен быть 001
        assertThat(number2).isEqualTo(String.format("%s-%d-001", documentType, Year.now().getValue()));

        assertThat(number1).isEqualTo(number2);
    }
}
