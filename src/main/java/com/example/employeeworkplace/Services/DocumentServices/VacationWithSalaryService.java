package com.example.employeeworkplace.Services.DocumentServices;

import com.example.employeeworkplace.Models.ConstantsAndEnums.ConstantsDocuments;
import com.example.employeeworkplace.Models.Primary.VacationWithSalary;
import com.example.employeeworkplace.Repositories.Primary.VacationWithSalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationWithSalaryService {

    private final VacationWithSalaryRepository vacationWithSalaryRepository;


    /**
     * Сохраняет или обновляет документ отпуска с сохранением заработной платы.
     * Устанавливает название документа в соответствии с типом документа.
     *
     * @param vacationWithSalary документ отпуска с сохранением заработной платы для сохранения или обновления
     * @return сохранённый документ
     */
    public VacationWithSalary save(VacationWithSalary vacationWithSalary) {
        log.info("Сохранение или обновление отпуска с сохранением заработной платы с id: {}", vacationWithSalary.getId());
        vacationWithSalary.setNameOfTheDocument(ConstantsDocuments.VacationWithSalary);
        VacationWithSalary savedVacation = vacationWithSalaryRepository.save(vacationWithSalary);
        log.debug("Отпуск с сохранением заработной платы с id {} успешно сохранён.", savedVacation.getId());
        return savedVacation;
    }

}
