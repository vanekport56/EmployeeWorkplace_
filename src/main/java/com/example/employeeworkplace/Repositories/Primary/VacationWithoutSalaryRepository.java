package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.VacationWithoutSalary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link VacationWithoutSalary}.
 * <p>
 * Предоставляет методы для выполнения операций с таблицей, в которой хранятся записи документации по отпускам без сохранения заработной платы.
 * </p>
 */
public interface VacationWithoutSalaryRepository extends JpaRepository<VacationWithoutSalary, Long> {
}
