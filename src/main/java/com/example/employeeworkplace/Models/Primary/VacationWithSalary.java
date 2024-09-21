package com.example.employeeworkplace.Models.Primary;

import com.example.employeeworkplace.Models.ConstantsAndEnums.ConstantsDocuments;
import jakarta.persistence.*;

/**
 * Класс представляет документацию по отпуску с сохранением зарплаты.
 *
 * <p>Этот класс расширяет {@link VacationDocumentation} и использует значение {@code "WITH_SALARY"}
 * для определения типа отпуска в таблице.</p>
 */
@Entity
@DiscriminatorValue("WITH_SALARY")
@Table(name = "vacation_with_salary")

public class VacationWithSalary extends VacationDocumentation {
    /**
     * Устанавливает имя документа для отпуска с сохранением зарплаты.
     *
     * @param nameOfTheDocument имя документа
     */
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vacation_with_salary_seq")
    @Override

    public void setNameOfTheDocument(String nameOfTheDocument) {
        super.setNameOfTheDocument(ConstantsDocuments.VacationWithSalary);
    }

}
