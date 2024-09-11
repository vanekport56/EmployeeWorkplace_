package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.TaxCertificate;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями {@link TaxCertificate}.
 * Наследует общие методы из {@link GenericCertificateRepository}.
 */
@Repository
public interface TaxCertificateRepository extends GenericCertificateRepository<TaxCertificate> {

}
