package com.example.employeeworkplace.Models.Primary;

import com.example.employeeworkplace.Models.ConstantsDocuments;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * Класс представляет документацию по отпуску без сохранения зарплаты.
 *
 * <p>Этот класс расширяет {@link VacationDocumentation} и использует значение {@code "WITHOUT_SALARY"}
 * для определения типа отпуска в таблице.</p>
 */
@Entity
@DiscriminatorValue("WITHOUT_SALARY")
@Table(name = "vacation_without_salary")
public class VacationWithoutSalary extends VacationDocumentation {

    /**
     * Устанавливает имя документа для отпуска без сохранения зарплаты.
     *
     * @param nameOfTheDocument имя документа
     */
    @Override
    public void setNameOfTheDocument(String nameOfTheDocument) {
        super.setNameOfTheDocument(ConstantsDocuments.VacationWithoutSalary);
    }

}
