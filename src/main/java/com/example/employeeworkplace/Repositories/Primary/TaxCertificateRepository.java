package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.TaxCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link TaxCertificate}.
 * <p>
 * Предоставляет методы для выполнения операций с таблицей {@code tax_certificate}.
 * </p>
 */
@Repository
public interface TaxCertificateRepository extends JpaRepository<TaxCertificate, Long> {

    /**
     * Выполняет поиск налоговых сертификатов с использованием заданных фильтров.
     *
     * @param name      Название сертификата (может быть {@code null}).
     * @param startDate Дата начала поиска (может быть {@code null}).
     * @param endDate   Дата окончания поиска (может быть {@code null}).
     * @param userId    Идентификатор пользователя (может быть {@code null}).
     * @param hasFile   Флаг, указывающий, должен ли сертификат иметь файл (может быть {@code null}).
     * @return Список налоговых сертификатов, соответствующих заданным фильтрам.
     */
    @Query("SELECT c FROM TaxCertificate c WHERE (:name IS NULL OR c.nameOfTheCertificate = :name) " +
            "AND (:startDate IS NULL OR c.dateOfCreation >= :startDate) " +
            "AND (:endDate IS NULL OR c.dateOfCreation <= :endDate) " +
            "AND (:userId IS NULL OR c.userId = :userId) " +
            "AND (:hasFile IS NULL OR (c.file IS NOT NULL AND c.file <> ''))")
    List<TaxCertificate> findWithFilters(String name, Date startDate, Date endDate, Long userId, Boolean hasFile);
}
