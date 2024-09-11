package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Repositories.Primary.*;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import com.example.employeeworkplace.Services.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.VacationWithSalaryService;
import com.example.employeeworkplace.Services.VacationWithoutSalaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class DocumentGeneratorServiceTest {

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

    @Test
    public void testGenerateAndSaveDocuments() {
        documentGeneratorService.generateAndSaveDocuments();

        assertThat(salaryOffsetRepository.findAll()).hasSize(15);
        assertThat(vacationWithoutSalaryRepository.findAll()).hasSize(10);
        assertThat(vacationWithSalaryRepository.findAll()).hasSize(10);
        assertThat(certificateRepository.findAll()).hasSize(10);
        assertThat(taxCertificateRepository.findAll()).hasSize(10);
    }
}
