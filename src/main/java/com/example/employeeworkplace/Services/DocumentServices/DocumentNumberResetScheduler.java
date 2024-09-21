package com.example.employeeworkplace.Services.DocumentServices;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Планировщик для сброса счетчика номеров документов.
 */
@Component
public class DocumentNumberResetScheduler {

    private final DocumentNumberGeneratorService documentNumberGeneratorService;

    /**
     * Конструктор для внедрения зависимости {@link DocumentNumberGeneratorService}.
     *
     * @param documentNumberGeneratorService Сервис для генерации номеров документов
     */
    public DocumentNumberResetScheduler(DocumentNumberGeneratorService documentNumberGeneratorService) {
        this.documentNumberGeneratorService = documentNumberGeneratorService;
    }

    /**
     * Запланированное выполнение сброса счетчика номеров документов.
     * Выполняется 1 января каждого года в 00:00.
     */
    @Scheduled(cron = "0 0 0 1 1 *")
    public void resetDocumentNumberCounter() {
        documentNumberGeneratorService.resetCounter();
    }
}
