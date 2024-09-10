package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link SalaryOffset}.
 * <p>
 * Предоставляет методы для выполнения операций с таблицей {@code salary_offset}.
 * </p>
 */
@Repository
public interface SalaryOffsetRepository extends JpaRepository<SalaryOffset, Long> {

}
