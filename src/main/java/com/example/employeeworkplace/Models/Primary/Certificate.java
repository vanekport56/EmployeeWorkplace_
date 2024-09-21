package com.example.employeeworkplace.Models.Primary;

import com.example.employeeworkplace.Models.ConstantsAndEnums.ConstantsOrderedDocuments;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Сущность для сертификатов.
 *
 * <p>Класс представляет сертификаты и наследует от {@link OrderedDocuments}.
 * Определяет тип сертификата как константу {@link ConstantsOrderedDocuments#Certificate}.</p>
 */
@Entity
public class Certificate extends OrderedDocuments {

    /**
     * Возвращает тип сертификата.
     *
     * @return Тип сертификата, определенный как {@link ConstantsOrderedDocuments#Certificate}
     */
    @Override
    @Column(name = "type_of_the_certificate", nullable = false, updatable = false)
    public String getTypeOfTheCertificate() {
        return ConstantsOrderedDocuments.Certificate;
    }
}
