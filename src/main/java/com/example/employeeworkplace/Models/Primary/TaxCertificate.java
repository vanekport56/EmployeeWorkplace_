package com.example.employeeworkplace.Models.Primary;

import com.example.employeeworkplace.Models.ConstantsOrderedDocuments;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Сущность для заказа сертификата типа 2-НДФЛ.
 *
 * <p>Этот класс расширяет {@link OrderedDocuments} и представляет собой налоговый сертификат с типом сертификата, установленным как {@link ConstantsOrderedDocuments#TaxCertificate}.</p>
 */
@Entity
@Table(name = "tax_certificate")
public class TaxCertificate extends OrderedDocuments {

    /**
     * Возвращает тип сертификата, установленный как {@link ConstantsOrderedDocuments#TaxCertificate}.
     *
     * @return тип сертификата
     */
    @Override
    @Column(name = "type_of_the_certificate", nullable = false, updatable = false)
    public String getTypeOfTheCertificate() {
        return ConstantsOrderedDocuments.TaxCertificate;
    }
}
