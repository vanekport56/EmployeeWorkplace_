package com.example.employeeworkplace.Services.DocumentServices;

import com.example.employeeworkplace.Models.ConstantsAndEnums.ConstantsDocuments;
import com.example.employeeworkplace.Models.Primary.VacationWithoutSalary;
import com.example.employeeworkplace.Repositories.Primary.VacationWithoutSalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class VacationWithoutSalaryService {

    private final VacationWithoutSalaryRepository vacationWithoutSalaryRepository;


    /**
     * Сохраняет или обновляет документ отпуска без содержания. Устанавливает
     * название документа в соответствии с типом документа.
     *
     * @param vacationWithoutSalary документ отпуска без содержания для сохранения или обновления
     * @return сохранённый документ
     */
    public VacationWithoutSalary save(VacationWithoutSalary vacationWithoutSalary) {
        log.info("Сохранение или обновление отпуска без содержания с id: {}", vacationWithoutSalary.getId());
        vacationWithoutSalary.setNameOfTheDocument(ConstantsDocuments.VacationWithoutSalary);
        VacationWithoutSalary savedVacation = vacationWithoutSalaryRepository.save(vacationWithoutSalary);
        log.debug("Отпуск без содержания с id {} успешно сохранён.", savedVacation.getId());
        return savedVacation;
    }
}
