package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.OrderedDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями {@link OrderedDocuments}.
 * Наследует методы для стандартных операций CRUD из {@link JpaRepository}.
 */
@Repository
public interface OrderedDocumentsRepository extends JpaRepository<OrderedDocuments, Long> {

}
