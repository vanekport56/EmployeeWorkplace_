package com.example.employeeworkplace.Models.ConstantsAndEnums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление, представляющее пол пользователя.
 */
@AllArgsConstructor
@Getter
public enum Gender {
    MALE("Мужчина"),
    FEMALE("Женщина");

    /**
     * Описание пола.
     */
    private final String description;
}
