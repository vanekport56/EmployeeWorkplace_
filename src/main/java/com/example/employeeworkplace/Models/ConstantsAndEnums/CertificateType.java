package com.example.employeeworkplace.Models.ConstantsAndEnums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Перечисление, представляющее типы сертификатов.
 */

@Getter
public enum CertificateType {
    /**
     * Тип сертификата "2-НДФЛ" с возможными вариантами использования.
     */
    TAX_CERTIFICATE("2-НДФЛ", Arrays.asList(
            "Для декларации",
            "Подтверждение доходов")),

    /**
     * Общий тип сертификата с различными вариантами использования.
     */
    CERTIFICATE("Справка", Arrays.asList(
            "С места работы",
            "О доходах",
            "О начисленных страховых взносах",
            "О неполучении единовременного пособия при рождении ребенка",
            "О неполучении ежемесячного пособия по уходу за ребенком до 1.5 лет",
            "О неполучении пособия по нетрудоспособности",
            "О неиспользовании (частичном использовании) дополнительных выходных дней по уходу за детьми-инвалидами"));

    /**
     * -- GETTER --
     *  Получает отображаемое имя типа сертификата.
     *
     */
    private final String displayName;
    /**
     * -- GETTER --
     *  Получает список возможных названий для типа сертификата.
     *
     */
    private final List<String> possibleNames;

    /**
     * Конструктор для инициализации типа сертификата.
     *
     * @param displayName   Отображаемое имя типа сертификата.
     * @param possibleNames Список возможных названий для этого типа сертификата.
     */
    CertificateType(String displayName, List<String> possibleNames) {
        this.displayName = displayName;
        this.possibleNames = possibleNames;
    }
}
