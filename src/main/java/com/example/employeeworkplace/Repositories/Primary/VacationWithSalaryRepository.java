package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.VacationWithSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link VacationWithSalary}.
 * <p>
 * Предоставляет методы для выполнения операций с таблицей, в которой хранятся записи документации по отпускам с сохранением заработной платы.
 * </p>
 */
@Repository
public interface VacationWithSalaryRepository extends JpaRepository<VacationWithSalary, Long> {
}
