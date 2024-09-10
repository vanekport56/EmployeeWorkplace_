package com.example.employeeworkplace.Services;

import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DocumentNumberGeneratorService {


    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Генерирует номер документа в формате: <documentType>-<year>-<number>
     * @param documentType Тип документа
     * @return Сформированный номер документа
     */
    public String generateDocumentNumber(String documentType) {
        int year = Year.now().getValue();
        int number = counter.incrementAndGet();


        return String.format("%s-%04d-%03d", documentType, year, number);
    }

    /**
     * Сбрасывает счетчик для новой генерации документов
     */
    public void resetCounter() {
        counter.set(0);
    }
}
