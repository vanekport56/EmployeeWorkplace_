package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Primary.SalaryOffsetRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с документами SalaryOffset.
 * Предоставляет методы для фильтрации, получения, сохранения и удаления документов.
 */
@Service
@RequiredArgsConstructor
public class SalaryOffsetService {

    private static final Logger logger = LoggerFactory.getLogger(SalaryOffsetService.class);

    private final SalaryOffsetRepository salaryOffsetRepository;

    /**
     * Фильтрует документы SalaryOffset по текущему пользователю.
     *
     * @param documents список документов для фильтрации
     * @param currentUser текущий пользователь
     * @return список документов, принадлежащих текущему пользователю
     */
    public List<SalaryOffset> filterByCurrentUser(List<SalaryOffset> documents, User currentUser) {
        logger.info("Фильтрация документов по пользователю: {}", currentUser.getId());
        return documents.stream()
                .filter(doc -> doc.getUserId() != null && doc.getUserId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех документов SalaryOffset.
     *
     * @return список всех документов
     */
    public List<SalaryOffset> listSalaryOffset() {
        logger.info("Получение списка всех документов SalaryOffset");
        return salaryOffsetRepository.findAll();
    }

    /**
     * Получает документ SalaryOffset по его ID.
     *
     * @param id идентификатор документа
     * @return найденный документ или null, если документ не найден
     */
    public SalaryOffset getSalaryOffsetById(Long id) {
        logger.info("Поиск документа SalaryOffset по ID: {}", id);
        return salaryOffsetRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет новый документ SalaryOffset.
     *
     * @param salaryOffset документ для сохранения
     * @return сохраненный документ
     */
    public SalaryOffset saveSalaryOffset(SalaryOffset salaryOffset) {
        logger.info("Сохранение нового документа SalaryOffset");
        return salaryOffsetRepository.save(salaryOffset);
    }

    /**
     * Обновляет существующий документ SalaryOffset по его ID.
     *
     * @param id идентификатор обновляемого документа
     * @param updatedSalaryOffset обновленный документ
     * @return обновленный документ или null, если документ не найден
     */
    public SalaryOffset updateSalaryOffset(Long id, SalaryOffset updatedSalaryOffset) {
        logger.info("Обновление документа SalaryOffset с ID: {}", id);
        if (salaryOffsetRepository.existsById(id)) {
            updatedSalaryOffset.setId(id);
            logger.debug("Документ обновлен: {}", updatedSalaryOffset);
            return salaryOffsetRepository.save(updatedSalaryOffset);
        }
        logger.warn("Документ с ID {} не найден для обновления", id);
        return null;
    }

    /**
     * Удаляет документ SalaryOffset по его ID.
     *
     * @param id идентификатор документа для удаления
     */
    public void deleteSalaryOffset(Long id) {
        logger.info("Удаление документа SalaryOffset с ID: {}", id);
        salaryOffsetRepository.deleteById(id);
    }
}
