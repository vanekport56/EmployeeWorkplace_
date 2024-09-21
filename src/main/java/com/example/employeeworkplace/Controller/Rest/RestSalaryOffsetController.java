package com.example.employeeworkplace.Controller.Rest;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.SalaryAndCertificateService.SalaryOffsetService;
import com.example.employeeworkplace.Services.UserServices.UserClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для работы с данными зарплатных надбавок.
 */
@Slf4j
@RestController
@RequestMapping("/salary-offsets")
@RequiredArgsConstructor
public class RestSalaryOffsetController {



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
        log.debug("Запрос на получение зарплатной надбавки с идентификатором: {}", id);

        SalaryOffset salaryOffset = salaryOffsetService.getSalaryOffsetById(id);
        if (salaryOffset != null) {

            User user = userClientService.getUserById(salaryOffset.getUserId());
            if (user != null) {
                salaryOffset.setUserId(user.getId());
                log.debug("Данные пользователя обновлены для зарплатной надбавки с идентификатором: {}", id);
            } else {
                log.warn("Пользователь не найден для зарплатной надбавки с идентификатором: {}", id);
            }
        } else {
            log.error("Зарплатная надбавка не найдена с идентификатором: {}", id);
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
        log.debug("Создание новой зарплатной надбавки: {}", salaryOffset);
        SalaryOffset createdSalaryOffset = salaryOffsetService.saveSalaryOffset(salaryOffset);
        log.info("Зарплатная надбавка успешно создана: {}", createdSalaryOffset);
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
        log.debug("Обновление зарплатной надбавки с идентификатором: {} данными: {}", id, updatedSalaryOffset);
        SalaryOffset updated = salaryOffsetService.updateSalaryOffset(id, updatedSalaryOffset);
        log.info("Зарплатная надбавка успешно обновлена: {}", updated);
        return updated;
    }

    /**
     * Удаляет зарплатную надбавку по идентификатору.
     *
     * @param id идентификатор зарплатной надбавки
     */
    @DeleteMapping("/{id}")
    public void deleteSalaryOffset(@PathVariable Long id) {
        log.debug("Удаление зарплатной надбавки с идентификатором: {}", id);
        salaryOffsetService.deleteSalaryOffset(id);
        log.info("Зарплатная надбавка успешно удалена с идентификатором: {}", id);
    }
}
