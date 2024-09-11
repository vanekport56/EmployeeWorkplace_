package com.example.employeeworkplace.Models.Primary;

import com.example.employeeworkplace.Models.ConstantsDocuments;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Сущность для представления документа "Salary Offset".
 *
 * <p>Этот класс расширяет {@link Document} и представляет собой документ зарплаты.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "salary_offset")
public class SalaryOffset extends Document {

    /**
     * Устанавливает название документа как {@link ConstantsDocuments#SalaryOffset}.
     *
     * @param nameOfTheDocument название документа
     */
    @Override
    public void setNameOfTheDocument(String nameOfTheDocument) {
        super.setNameOfTheDocument(ConstantsDocuments.SalaryOffset);
    }

    /**
     * Сумма денег, указанная в документе зарплаты.
     */
    @Column(name = "sum_of_money")
    private BigDecimal sumOfMoney;
}
