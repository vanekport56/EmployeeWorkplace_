package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.ConstantsDocuments;
import com.example.employeeworkplace.Models.Primary.VacationWithoutSalary;
import com.example.employeeworkplace.Repositories.Primary.VacationWithoutSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationWithoutSalaryService {

    private final VacationWithoutSalaryRepository vacationWithoutSalaryRepository;
    private static final Logger logger = LoggerFactory.getLogger(VacationWithoutSalaryService.class);

    /**
     * Сохраняет или обновляет документ отпуска без содержания. Устанавливает
     * название документа в соответствии с типом документа.
     *
     * @param vacationWithoutSalary документ отпуска без содержания для сохранения или обновления
     * @return сохранённый документ
     */
    public VacationWithoutSalary save(VacationWithoutSalary vacationWithoutSalary) {
        logger.info("Сохранение или обновление отпуска без содержания с id: {}", vacationWithoutSalary.getId());
        vacationWithoutSalary.setNameOfTheDocument(ConstantsDocuments.VacationWithoutSalary);
        VacationWithoutSalary savedVacation = vacationWithoutSalaryRepository.save(vacationWithoutSalary);
        logger.debug("Отпуск без содержания с id {} успешно сохранён.", savedVacation.getId());
        return savedVacation;
    }
}
