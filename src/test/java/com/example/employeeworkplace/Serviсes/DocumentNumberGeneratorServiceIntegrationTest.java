package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интеграционные тесты для сервиса генерации номеров документов {@link DocumentNumberGeneratorService}.
 * Проверяют функциональность генерации и сброса счетчика номеров документов.
 */
@Slf4j
@SpringBootTest

public class DocumentNumberGeneratorServiceIntegrationTest {



    @Autowired
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    /**
     * Устанавливает начальное состояние перед каждым тестом.
     * Сбрасывает счетчик генератора номеров документов.
     */
    @BeforeEach
    void setUp() {
        documentNumberGeneratorService.resetCounter();
        log.debug("Счетчик номера документа сброшен.");
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

        log.debug("Сгенерированный номер документа 1: {}", number1);
        log.debug("Сгенерированный номер документа 2: {}", number2);

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

        log.debug("Сгенерированный номер документа до сброса: {}", number1);
        log.debug("Сгенерированный номер документа после сброса: {}", number2);

        assertThat(number2).isEqualTo(String.format("%s-%d-001", documentType, Year.now().getValue()));

        assertThat(number1).isEqualTo(number2);
    }
}
