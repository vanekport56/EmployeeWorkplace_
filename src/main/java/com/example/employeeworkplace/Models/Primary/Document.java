package com.example.employeeworkplace.Models.Primary;

import com.example.employeeworkplace.Models.Secondary.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * Абстрактный класс для документов.
 *
 * <p>Этот класс представляет базовую структуру документа, включая его идентификатор, официальную должность, название,
 * дату создания, номер документа и идентификатор пользователя. Наследуется другими типами документов.</p>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Document implements Serializable {

    /**
     * Уникальный идентификатор документа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    /**
     * Официальная должность, связанная с документом.
     */
    @Column(name = "official_position")
    private String officialPosition;

    /**
     * Название документа.
     */
    @Column(name = "name_of_the_document")
    private String nameOfTheDocument;

    /**
     * Дата создания документа.
     * По умолчанию устанавливается на текущую дату.
     */
    @Column(name = "date_of_creation")
    private Date dateOfCreation = new Date(System.currentTimeMillis());

    /**
     * Номер документа.
     * Должен быть уникальным.
     */
    @Column(name = "document_Number", unique = true)
    private String documentNumber;

    /**
     * Идентификатор пользователя, связанного с документом.
     */
    @Column(name = "user_id")
    private Long userId;


    @Transient
    private User transientUser;


    @Transient
    private String userFullName;
}
