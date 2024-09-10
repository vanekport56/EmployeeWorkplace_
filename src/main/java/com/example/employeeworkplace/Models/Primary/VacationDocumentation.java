package com.example.employeeworkplace.Models.Primary;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Абстрактный класс для представления документации по отпускам.
 *
 * <p>Этот класс расширяет {@link Document} и представляет собой общую документацию по отпуску с полями для даты начала и окончания отпуска, причины, статуса одобрения и информации о зарплате.</p>
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VacationType")
@Data
public abstract class VacationDocumentation extends Document {

    /**
     * Уникальный идентификатор документа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    /**
     * Дата начала отпуска.
     */
    @Column(name = "vacation_start_date")
    private LocalDate vacationStartDate;

    /**
     * Дата окончания отпуска.
     */
    @Column(name = "vacation_end_date")
    private LocalDate vacationEndDate;

    /**
     * Причина отпуска.
     */
    @Column(name = "Reason")
    private String reason;

    /**
     * Статус одобрения отпуска.
     */
    @Column(name = "Approval_Status")
    private String approvalStatus;

    /**
     * Указывает, выплачивается ли зарплата в период отпуска.
     */
    @Column(name = "is_with_salary")
    private Boolean isWithSalary;

    /**
     * Форматированная дата начала отпуска.
     */
    @Transient
    private String formattedVacationStartDate;

    /**
     * Форматированная дата окончания отпуска.
     */
    @Transient
    private String formattedVacationEndDate;
}
