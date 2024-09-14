package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.OrderedDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Обобщённый интерфейс репозитория для сущностей, которые имеют имя сертификата и идентификатор пользователя.
 *
 * @param <T> Тип сущности
 */
public interface GenericCertificateRepository<T extends OrderedDocuments> extends JpaRepository<T, Long> {

    /**
     * Находит сертификаты по заданным фильтрам.
     *
     * @param name   Название сертификата (может быть {@code null}).
     * @param userId Идентификатор пользователя (может быть {@code null}).
     * @return Список сертификатов, соответствующих указанным фильтрам.
     */
    @Query("SELECT t FROM #{#entityName} t WHERE " +
            "(:name IS NULL OR t.nameOfTheCertificate LIKE %:name%) AND " +
            "(:userId IS NULL OR t.userId = :userId)")
    List<T> findWithFilters(@Param("name") String name,
                            @Param("userId") Long userId);
}
