package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.VacationDocumentation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link VacationDocumentation}.
 * <p>
 * Предоставляет методы для выполнения операций с таблицей, в которой хранятся записи документации по отпускам.
 * </p>
 */
public interface VacationDocumentationRepository extends JpaRepository<VacationDocumentation, Long> {
}
