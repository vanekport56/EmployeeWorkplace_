package com.example.employeeworkplace.Models.Primary;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Сущность для хранения информации о файлах.
 * Хранит данные о загруженных файлах, такие как имя файла, путь к файлу, размер и время загрузки.
 */
@Entity
@Table(name = "files")
@Data
public class FileEntity {

    /**
     * Уникальный идентификатор файла.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Имя файла.
     */
    @Column(nullable = false)
    private String fileName;

    /**
     * Путь к файлу на сервере.
     */
    @Column(nullable = false)
    private String filePath;

    /**
     * Размер файла в байтах.
     */
    @Column(nullable = false)
    private Long fileSize;

    /**
     * Время загрузки файла.
     */
    @Column
    private LocalDateTime uploadTime;
}
