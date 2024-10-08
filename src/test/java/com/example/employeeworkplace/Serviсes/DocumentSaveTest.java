package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.ConstantsAndEnums.ConstantsOrderedDocuments;
import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Primary.VacationWithoutSalary;
import com.example.employeeworkplace.Models.Primary.VacationWithSalary;
import com.example.employeeworkplace.Models.Primary.Certificate;
import com.example.employeeworkplace.Models.Primary.TaxCertificate;
import com.example.employeeworkplace.Repositories.Primary.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Disabled
public class DocumentSaveTest {

    @Autowired
    private DocumentGeneratorService documentGeneratorService;

    @Autowired
    private SalaryOffsetRepository salaryOffsetRepository;

    @Autowired
    private VacationWithoutSalaryRepository vacationWithoutSalaryRepository;

    @Autowired
    private VacationWithSalaryRepository vacationWithSalaryRepository;



    @Autowired
    private TaxCertificateRepository taxCertificateRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @BeforeEach
    public void setUp() {

        salaryOffsetRepository.deleteAll();
        vacationWithoutSalaryRepository.deleteAll();
        vacationWithSalaryRepository.deleteAll();
        certificateRepository.deleteAll();
        taxCertificateRepository.deleteAll();
    }

    /**
     * Тестирование метода generateAndSaveDocuments для проверки корректности генерации документов.
     * <p>
     * Метод вызывается для генерации всех типов документов. После генерации проверяется,
     * что все репозитории содержат ожидаемое количество записей.
     */
    @Test
    public void testGenerateAndSaveDocuments() {
        documentGeneratorService.generateAndSaveDocuments();

        assertThat(salaryOffsetRepository.findAll()).hasSize(15);
        assertThat(vacationWithoutSalaryRepository.findAll()).hasSize(10);
        assertThat(vacationWithSalaryRepository.findAll()).hasSize(10);
        assertThat(certificateRepository.findAll()).hasSize(10);
        assertThat(taxCertificateRepository.findAll()).hasSize(10);
    }

    /**
     * Тестирование метода generateSalaryOffsets для проверки корректности генерации SalaryOffset.
     * <p>
     * Метод вызывается для генерации SalaryOffset. После генерации проверяется,
     * что в репозитории есть ожидаемое количество записей и что данные сохранены корректно.
     */
    @Test
    public void testGenerateSalaryOffsets() {
        documentGeneratorService.generateSalaryOffsets();

        assertThat(salaryOffsetRepository.findAll()).hasSize(15);
        SalaryOffset salaryOffset = salaryOffsetRepository.findAll().get(0);
        assertThat(salaryOffset.getNameOfTheDocument()).startsWith("Зачет в зарплату");

    }

    /**
     * Тестирование метода generateVacationWithoutSalaries для проверки корректности генерации VacationWithoutSalary.
     * <p>
     * Метод вызывается для генерации VacationWithoutSalary. После генерации проверяется,
     * что в репозитории есть ожидаемое количество записей и что данные сохранены корректно.
     */
    @Test
    public void testGenerateVacationWithoutSalaries() {
        documentGeneratorService.generateVacationWithoutSalaries();

        assertThat(vacationWithoutSalaryRepository.findAll()).hasSize(10);
        VacationWithoutSalary vacationWithoutSalary = vacationWithoutSalaryRepository.findAll().get(0);
        assertThat(vacationWithoutSalary.getReason()).isEqualTo("Personal");
    }

    /**
     * Тестирование метода generateVacationWithSalaries для проверки корректности генерации VacationWithSalary.
     * <p>
     * Метод вызывается для генерации VacationWithSalary. После генерации проверяется,
     * что в репозитории есть ожидаемое количество записей и что данные сохранены корректно.
     */
    @Test
    public void testGenerateVacationWithSalaries() {
        documentGeneratorService.generateVacationWithSalaries();

        assertThat(vacationWithSalaryRepository.findAll()).hasSize(10);
        VacationWithSalary vacationWithSalary = vacationWithSalaryRepository.findAll().get(0);
        assertThat(vacationWithSalary.getReason()).isEqualTo("Personal");
    }

    /**
     * Тестирование метода generateCertificates для проверки корректности генерации Certificate.
     * <p>
     * Метод вызывается для генерации Certificate. После генерации проверяется,
     * что в репозитории есть ожидаемое количество записей и что данные сохранены корректно.
     */
    @Test
    public void testGenerateCertificates() {
        documentGeneratorService.generateCertificates();

        assertThat(certificateRepository.findAll()).hasSize(10);
        Certificate certificate = certificateRepository.findAll().get(0);
        assertThat(certificate.getTypeOfTheCertificate()).isEqualTo(ConstantsOrderedDocuments.Certificate);
    }

    /**
     * Тестирование метода generateTaxCertificates для проверки корректности генерации TaxCertificate.
     * <p>
     * Метод вызывается для генерации TaxCertificate. После генерации проверяется,
     * что в репозитории есть ожидаемое количество записей и что данные сохранены корректно.
     */
    @Test
    public void testGenerateTaxCertificates() {
        documentGeneratorService.generateTaxCertificates();

        assertThat(taxCertificateRepository.findAll()).hasSize(10);
        TaxCertificate taxCertificate = taxCertificateRepository.findAll().get(0);
        assertThat(taxCertificate.getTypeOfTheCertificate()).isEqualTo(ConstantsOrderedDocuments.TaxCertificate);
    }
}
