package com.example.employeeworkplace.Services.SalaryAndCertificateService;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Primary.SalaryOffsetRepository;
import com.example.employeeworkplace.Security.Configuration.Secured;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с документами SalaryOffset.
 * Предоставляет методы для фильтрации, получения, сохранения и удаления документов.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryOffsetService {


    private final SalaryOffsetRepository salaryOffsetRepository;

    /**
     * Фильтрует документы SalaryOffset по текущему пользователю.
     *
     * @param documents список документов для фильтрации
     * @param currentUser текущий пользователь
     * @return список документов, принадлежащих текущему пользователю
     */

    public List<SalaryOffset> filterByCurrentUser(List<SalaryOffset> documents, User currentUser) {
        log.info("Фильтрация документов по пользователю: {}", currentUser.getId());
        return documents.stream()
                .filter(doc -> doc.getUserId() != null && doc.getUserId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех документов SalaryOffset.
     *
     * @return список всех документов
     */
    @Cacheable("SalaryOffset")
    public List<SalaryOffset> listSalaryOffset() {
        log.info("Получение списка всех документов SalaryOffset");
        return salaryOffsetRepository.findAll();
    }

    /**
     * Получает документ SalaryOffset по его ID.
     *
     * @param id идентификатор документа
     * @return найденный документ или null, если документ не найден
     */
    public SalaryOffset getSalaryOffsetById(Long id) {
        log.info("Поиск документа SalaryOffset по ID: {}", id);
        return salaryOffsetRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет новый документ SalaryOffset.
     *
     * @param salaryOffset документ для сохранения
     * @return сохраненный документ
     */
    public SalaryOffset saveSalaryOffset(SalaryOffset salaryOffset) {
        log.info("Сохранение нового документа SalaryOffset");
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
        log.info("Обновление документа SalaryOffset с ID: {}", id);
        if (salaryOffsetRepository.existsById(id)) {
            updatedSalaryOffset.setId(id);
            log.debug("Документ обновлен: {}", updatedSalaryOffset);
            return salaryOffsetRepository.save(updatedSalaryOffset);
        }
        log.warn("Документ с ID {} не найден для обновления", id);
        return null;
    }

    /**
     * Удаляет документ SalaryOffset по его ID.
     *
     * @param id идентификатор документа для удаления
     */
    @Secured("admin")
    public void deleteSalaryOffset(Long id) {
        log.info("Удаление документа SalaryOffset с ID: {}", id);
        salaryOffsetRepository.deleteById(id);
    }
}
