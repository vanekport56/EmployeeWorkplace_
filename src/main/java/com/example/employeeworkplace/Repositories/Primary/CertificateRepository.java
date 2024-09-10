package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Certificate}.
 * <p>
 * Предоставляет методы для выполнения операций с таблицей {@code certificate}.
 * </p>
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    /**
     * Находит сертификаты по заданным фильтрам.
     *
     * @param name      Название сертификата (может быть {@code null}).
     * @param startDate Дата начала (может быть {@code null}).
     * @param endDate   Дата окончания (может быть {@code null}).
     * @param userId    Идентификатор пользователя (может быть {@code null}).
     * @param hasFile   Флаг наличия файла (может быть {@code null}).
     * @return Список сертификатов, соответствующих указанным фильтрам.
     */
    @Query("SELECT c FROM Certificate c WHERE (:name IS NULL OR c.nameOfTheCertificate = :name) " +
            "AND (:startDate IS NULL OR c.dateOfCreation >= :startDate) " +
            "AND (:endDate IS NULL OR c.dateOfCreation <= :endDate) " +
            "AND (:userId IS NULL OR c.userId = :userId) " +
            "AND (:hasFile IS NULL OR (c.file IS NOT NULL AND c.file <> ''))")
    List<Certificate> findWithFilters(@Param("name") String name,
                                      @Param("startDate") Date startDate,
                                      @Param("endDate") Date endDate,
                                      @Param("userId") Long userId,
                                      @Param("hasFile") Boolean hasFile);
}
