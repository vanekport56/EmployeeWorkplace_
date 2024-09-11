package com.example.employeeworkplace.Repositories.Primary;

import com.example.employeeworkplace.Models.Primary.Certificate;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями {@link Certificate}.
 * Наследует общие методы из {@link GenericCertificateRepository}.
 */
@Repository
public interface CertificateRepository extends GenericCertificateRepository<Certificate> {

}
