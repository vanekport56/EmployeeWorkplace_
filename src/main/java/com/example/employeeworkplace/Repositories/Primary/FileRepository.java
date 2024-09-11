package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link FileEntity}.
 * Наследует методы для стандартных операций CRUD из {@link JpaRepository}.
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    /**
     * Поиск сущности {@link FileEntity} по имени файла.
     *
     * @param fileName имя файла
     * @return сущность {@link FileEntity} или пустой {@link Optional}, если сущность не найдена
     */
    List<FileEntity> findByFileName(String fileName);
}
