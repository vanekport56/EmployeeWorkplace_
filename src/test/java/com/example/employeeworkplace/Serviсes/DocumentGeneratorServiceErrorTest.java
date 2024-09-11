package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.*;
import com.example.employeeworkplace.Repositories.Primary.*;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import com.example.employeeworkplace.Services.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.VacationWithSalaryService;
import com.example.employeeworkplace.Services.VacationWithoutSalaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class DocumentGeneratorServiceErrorTest {

    @Autowired
    private DocumentGeneratorService documentGeneratorService;

    @Autowired
    private SalaryOffsetRepository salaryOffsetRepository;

    @Autowired
    private VacationWithoutSalaryRepository vacationWithoutSalaryRepository;

    @Autowired
    private VacationWithSalaryRepository vacationWithSalaryRepository;

    @Autowired
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    @Autowired
    private VacationWithoutSalaryService vacationWithoutSalaryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacationWithSalaryService vacationWithSalaryService;

    @Autowired
    private TaxCertificateRepository taxCertificateRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @BeforeEach
    public void setUp() {
        // Очистка данных перед каждым тестом
        salaryOffsetRepository.deleteAll();
        vacationWithoutSalaryRepository.deleteAll();
        vacationWithSalaryRepository.deleteAll();
        certificateRepository.deleteAll();
        taxCertificateRepository.deleteAll();
    }

    /**
     * Тестирование ошибки при сохранении некорректного SalaryOffset.
     * <p>
     * В этом тесте вызывается метод generateSalaryOffsets с данными, которые приводят к ошибке сохранения,
     * и проверяется, что ошибка выбрасывается корректно.
     */
    @Test
    public void testGenerateSalaryOffsetsError() {

        documentGeneratorService.generateSalaryOffsets();

        assertThatThrownBy(() -> salaryOffsetRepository.save(new SalaryOffset()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    /**
     * Тестирование ошибки при сохранении некорректного VacationWithoutSalary.
     * <p>
     * В этом тесте вызывается метод generateVacationWithoutSalaries с данными, которые приводят к ошибке сохранения,
     * и проверяется, что ошибка выбрасывается корректно.
     */
        @Test
        public void testGenerateVacationWithoutSalariesError() {

            VacationWithoutSalary faultyVacation = new VacationWithoutSalary();
            faultyVacation.setVacationStartDate(LocalDate.now());
            faultyVacation.setVacationEndDate(LocalDate.now().plusDays(7));
            // Не указываем обязательные поля, чтобы вызвать ошибку
            faultyVacation.setReason(null);
            faultyVacation.setApprovalStatus(null);

            assertThatThrownBy(() -> vacationWithoutSalaryService.save(faultyVacation))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }

        /**
         * Тестирование ошибки при сохранении некорректного VacationWithSalary.
         * <p>
         * В этом тесте вызывается метод generateVacationWithSalaries с данными, которые приводят к ошибке сохранения,
         * и проверяется, что ошибка выбрасывается корректно.
         */
        @Test
        public void testGenerateVacationWithSalariesError() {

            VacationWithSalary faultyVacation = new VacationWithSalary();
            faultyVacation.setVacationStartDate(LocalDate.now());
            faultyVacation.setVacationEndDate(LocalDate.now().plusDays(7));

            faultyVacation.setReason(null);
            faultyVacation.setApprovalStatus(null);

            assertThatThrownBy(() -> vacationWithSalaryService.save(faultyVacation))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }

        /**
         * Тестирование ошибки при сохранении некорректного Certificate.
         * <p>
         * В этом тесте вызывается метод generateCertificates с данными, которые приводят к ошибке сохранения,
         * и проверяется, что ошибка выбрасывается корректно.
         */
        @Test
        public void testGenerateCertificatesError() {

            Certificate faultyCertificate = new Certificate();
            faultyCertificate.setNameOfTheCertificate(null);
            faultyCertificate.setTypeOfTheCertificate(null);

            assertThatThrownBy(() -> certificateRepository.save(faultyCertificate))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }

        /**
         * Тестирование ошибки при сохранении некорректного TaxCertificate.
         * <p>
         * В этом тесте вызывается метод generateTaxCertificates с данными, которые приводят к ошибке сохранения,
         * и проверяется, что ошибка выбрасывается корректно.
         */
        @Test
        public void testGenerateTaxCertificatesError() {

            TaxCertificate faultyTaxCertificate = new TaxCertificate();
            faultyTaxCertificate.setNameOfTheCertificate(null);
            faultyTaxCertificate.setTypeOfTheCertificate(null);

            assertThatThrownBy(() -> taxCertificateRepository.save(faultyTaxCertificate))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }
    }

