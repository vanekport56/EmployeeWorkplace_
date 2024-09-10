package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.ConstantsDocuments;
import com.example.employeeworkplace.Models.Primary.VacationWithSalary;
import com.example.employeeworkplace.Repositories.Primary.VacationWithSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VacationWithSalaryService {

    private final VacationWithSalaryRepository vacationWithSalaryRepository;
    private static final Logger logger = LoggerFactory.getLogger(VacationWithSalaryService.class);


    /**
     * Сохраняет или обновляет документ отпуска с сохранением заработной платы.
     * Устанавливает название документа в соответствии с типом документа.
     *
     * @param vacationWithSalary документ отпуска с сохранением заработной платы для сохранения или обновления
     * @return сохранённый документ
     */
    public VacationWithSalary save(VacationWithSalary vacationWithSalary) {
        logger.info("Сохранение или обновление отпуска с сохранением заработной платы с id: {}", vacationWithSalary.getId());
        vacationWithSalary.setNameOfTheDocument(ConstantsDocuments.VacationWithSalary);
        VacationWithSalary savedVacation = vacationWithSalaryRepository.save(vacationWithSalary);
        logger.debug("Отпуск с сохранением заработной платы с id {} успешно сохранён.", savedVacation.getId());
        return savedVacation;
    }

}
