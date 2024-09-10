package com.example.employeeworkplace.Models.Primary;

import com.example.employeeworkplace.Models.Secondary.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Абстрактный класс для заказных документов.
 *
 * <p>Этот класс представляет базовую структуру заказного документа, включая его идентификатор, название сертификата,
 * тип сертификата, дату создания, номер сертификата, идентификатор пользователя и файл. Наследуется другими типами документов.</p>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class OrderedDocuments {

    /**
     * Уникальный идентификатор документа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * Название сертификата.
     */
    @Column(name = "name_of_the_certificate")
    private String nameOfTheCertificate;

    /**
     * Тип сертификата.
     */
    @Column(name = "type_of_the_certificate")
    private String typeOfTheCertificate;

    /**
     * Дата создания документа.
     * По умолчанию устанавливается на текущую дату.
     */
    @Column(name = "date_of_creation")
    private Date dateOfCreation = new Date(System.currentTimeMillis());

    /**
     * Номер сертификата.
     * Должен быть уникальным.
     */
    @Column(name = "certificate_Number", unique = true)
    private String documentNumber;

    /**
     * Идентификатор пользователя, связанного с документом.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Пользователь, связанный с документом.
     * Это поле не сохраняется в базе данных.
     */
    @Transient
    private User transientUser;

    /**
     * Файл, связанный с документом.
     */
    @Column(name = "file")
    private String file;
}
