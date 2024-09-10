package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.SalaryOffsetService;
import com.example.employeeworkplace.Services.UserClientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для работы с данными зарплатных надбавок.
 */
@RestController
@RequestMapping("/salary-offsets")
@RequiredArgsConstructor
public class SalaryOffsetController {

    private static final Logger logger = LoggerFactory.getLogger(SalaryOffsetController.class);

    private final SalaryOffsetService salaryOffsetService;
    private final UserClientService userClientService;

    /**
     * Получает данные зарплатной надбавки по идентификатору.
     *
     * @param id идентификатор зарплатной надбавки
     * @return данные зарплатной надбавки
     */
    @GetMapping("/{id}")
    public SalaryOffset getSalaryOffset(@PathVariable Long id) {
        logger.debug("Запрос на получение зарплатной надбавки с идентификатором: {}", id);

        SalaryOffset salaryOffset = salaryOffsetService.getSalaryOffsetById(id);
        if (salaryOffset != null) {
            // Пример использования UserClientService
            User user = userClientService.getUserById(salaryOffset.getUserId());
            if (user != null) {
                salaryOffset.setUserId(user.getId());
                logger.debug("Данные пользователя обновлены для зарплатной надбавки с идентификатором: {}", id);
            } else {
                logger.warn("Пользователь не найден для зарплатной надбавки с идентификатором: {}", id);
            }
        } else {
            logger.error("Зарплатная надбавка не найдена с идентификатором: {}", id);
        }
        return salaryOffset;
    }

    /**
     * Создает новую зарплатную надбавку.
     *
     * @param salaryOffset данные новой зарплатной надбавки
     * @return созданная зарплатная надбавка
     */
    @PostMapping
    public SalaryOffset createSalaryOffset(@RequestBody SalaryOffset salaryOffset) {
        logger.debug("Создание новой зарплатной надбавки: {}", salaryOffset);
        SalaryOffset createdSalaryOffset = salaryOffsetService.saveSalaryOffset(salaryOffset);
        logger.info("Зарплатная надбавка успешно создана: {}", createdSalaryOffset);
        return createdSalaryOffset;
    }

    /**
     * Обновляет существующую зарплатную надбавку.
     *
     * @param id идентификатор зарплатной надбавки
     * @param updatedSalaryOffset обновленные данные зарплатной надбавки
     * @return обновленная зарплатная надбавка
     */
    @PutMapping("/{id}")
    public SalaryOffset updateSalaryOffset(@PathVariable Long id, @RequestBody SalaryOffset updatedSalaryOffset) {
        logger.debug("Обновление зарплатной надбавки с идентификатором: {} данными: {}", id, updatedSalaryOffset);
        SalaryOffset updated = salaryOffsetService.updateSalaryOffset(id, updatedSalaryOffset);
        logger.info("Зарплатная надбавка успешно обновлена: {}", updated);
        return updated;
    }

    /**
     * Удаляет зарплатную надбавку по идентификатору.
     *
     * @param id идентификатор зарплатной надбавки
     */
    @DeleteMapping("/{id}")
    public void deleteSalaryOffset(@PathVariable Long id) {
        logger.debug("Удаление зарплатной надбавки с идентификатором: {}", id);
        salaryOffsetService.deleteSalaryOffset(id);
        logger.info("Зарплатная надбавка успешно удалена с идентификатором: {}", id);
    }
}
