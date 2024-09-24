package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.ConstantsAndEnums.ConstantsOrderedDocuments;
import com.example.employeeworkplace.Models.Primary.*;
import com.example.employeeworkplace.Repositories.Primary.*;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.DocumentServices.VacationWithSalaryService;
import com.example.employeeworkplace.Services.DocumentServices.VacationWithoutSalaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional

public class DocumentGeneratorServiceSaveLoadTest {



    @Autowired
    private SalaryOffsetRepository salaryOffsetRepository;

    @Autowired
    private VacationWithoutSalaryRepository vacationWithoutSalaryRepository;

    @Autowired
    private VacationWithSalaryRepository vacationWithSalaryRepository;



    @Autowired
    private VacationWithoutSalaryService vacationWithoutSalaryService;


    @Autowired
    private VacationWithSalaryService vacationWithSalaryService;

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
     * Проверка сохранения и загрузки данных для SalaryOffset.
     * <p>
     * В этом тесте создается документ SalaryOffset, сохраняется в репозитории, затем загружается,
     * и проверяется, что сохраненные данные совпадают с исходными.
     */
    @Test
    public void testSaveAndLoadSalaryOffset() {
        SalaryOffset salaryOffset = new SalaryOffset();
        salaryOffset.setNameOfTheDocument("Test Salary Offset");
        salaryOffset.setOfficialPosition("Test Position");
        salaryOffset.setDateOfCreation(Date.valueOf(LocalDate.now()));
        salaryOffset.setUserId(1L);
        salaryOffset.setDocumentNumber("SO123");
        salaryOffset.setSumOfMoney(BigDecimal.valueOf(1000.00));

        salaryOffsetRepository.save(salaryOffset);

        SalaryOffset loadedSalaryOffset = salaryOffsetRepository.findById(salaryOffset.getId()).orElse(null);

        assertThat(loadedSalaryOffset).isNotNull();
        assertThat(loadedSalaryOffset.getNameOfTheDocument()).isEqualTo("Зачет в зарплату");
        assertThat(loadedSalaryOffset.getOfficialPosition()).isEqualTo("Test Position");
        assertThat(loadedSalaryOffset.getSumOfMoney()).isEqualByComparingTo(BigDecimal.valueOf(1000.00));
    }

    /**
     * Проверка сохранения и загрузки данных для VacationWithoutSalary.
     * <p>
     * В этом тесте создается документ VacationWithoutSalary, сохраняется в репозитории, затем загружается,
     * и проверяется, что сохраненные данные совпадают с исходными.
     */
    @Test
    public void testSaveAndLoadVacationWithoutSalary() {
        VacationWithoutSalary vacationWithoutSalary = new VacationWithoutSalary();
        vacationWithoutSalary.setVacationStartDate(LocalDate.now());
        vacationWithoutSalary.setVacationEndDate(LocalDate.now().plusDays(7));
        vacationWithoutSalary.setReason("Personal");
        vacationWithoutSalary.setApprovalStatus("Approved");
        vacationWithoutSalary.setUserId(1L);
        vacationWithoutSalary.setDocumentNumber("VWS123");

        vacationWithoutSalaryService.save(vacationWithoutSalary);

        VacationWithoutSalary loadedVacationWithoutSalary = vacationWithoutSalaryRepository.findById(vacationWithoutSalary.getId()).orElse(null);

        assertThat(loadedVacationWithoutSalary).isNotNull();
        assertThat(loadedVacationWithoutSalary.getReason()).isEqualTo("Personal");
        assertThat(loadedVacationWithoutSalary.getApprovalStatus()).isEqualTo("Approved");
    }

    /**
     * Проверка сохранения и загрузки данных для VacationWithSalary.
     * <p>
     * В этом тесте создается документ VacationWithSalary, сохраняется в репозитории, затем загружается,
     * и проверяется, что сохраненные данные совпадают с исходными.
     */
    @Test
    public void testSaveAndLoadVacationWithSalary() {
        VacationWithSalary vacationWithSalary = new VacationWithSalary();
        vacationWithSalary.setVacationStartDate(LocalDate.now());
        vacationWithSalary.setVacationEndDate(LocalDate.now().plusDays(7));
        vacationWithSalary.setReason("Personal");
        vacationWithSalary.setApprovalStatus("Approved");
        vacationWithSalary.setUserId(1L);
        vacationWithSalary.setDocumentNumber("VWS123");

        vacationWithSalaryService.save(vacationWithSalary);

        VacationWithSalary loadedVacationWithSalary = vacationWithSalaryRepository.findById(vacationWithSalary.getId()).orElse(null);

        assertThat(loadedVacationWithSalary).isNotNull();
        assertThat(loadedVacationWithSalary.getReason()).isEqualTo("Personal");
        assertThat(loadedVacationWithSalary.getApprovalStatus()).isEqualTo("Approved");
    }

    /**
     * Проверка сохранения и загрузки данных для Certificate.
     * <p>
     * В этом тесте создается документ Certificate, сохраняется в репозитории, затем загружается,
     * и проверяется, что сохраненные данные совпадают с исходными.
     */
    @Test
    public void testSaveAndLoadCertificate() {
        Certificate certificate = new Certificate();
        certificate.setUserId(1L);
        certificate.setNameOfTheCertificate("Test Certificate");
        certificate.setTypeOfTheCertificate(ConstantsOrderedDocuments.Certificate);
        certificate.setDateOfCreation(Date.valueOf(LocalDate.now()));
        certificate.setCertificateNumber("C123");

        certificateRepository.save(certificate);

        Certificate loadedCertificate = certificateRepository.findById(certificate.getId()).orElse(null);

        assertThat(loadedCertificate).isNotNull();
        assertThat(loadedCertificate.getNameOfTheCertificate()).isEqualTo("Test Certificate");
        assertThat(loadedCertificate.getTypeOfTheCertificate()).isEqualTo(ConstantsOrderedDocuments.Certificate);
    }

    /**
     * Проверка сохранения и загрузки данных для TaxCertificate.
     * <p>
     * В этом тесте создается документ TaxCertificate, сохраняется в репозитории, затем загружается,
     * и проверяется, что сохраненные данные совпадают с исходными.
     */
    @Test
    public void testSaveAndLoadTaxCertificate() {
        TaxCertificate taxCertificate = new TaxCertificate();
        taxCertificate.setUserId(1L);
        taxCertificate.setNameOfTheCertificate("Test Tax Certificate");
        taxCertificate.setTypeOfTheCertificate(ConstantsOrderedDocuments.TaxCertificate);
        taxCertificate.setDateOfCreation(Date.valueOf(LocalDate.now()));
        taxCertificate.setCertificateNumber("TC123");

        taxCertificateRepository.save(taxCertificate);

        TaxCertificate loadedTaxCertificate = taxCertificateRepository.findById(taxCertificate.getId()).orElse(null);

        assertThat(loadedTaxCertificate).isNotNull();
        assertThat(loadedTaxCertificate.getNameOfTheCertificate()).isEqualTo("Test Tax Certificate");
        assertThat(loadedTaxCertificate.getTypeOfTheCertificate()).isEqualTo(ConstantsOrderedDocuments.TaxCertificate);
    }
}
