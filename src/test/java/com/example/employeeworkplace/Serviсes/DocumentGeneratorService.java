package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.ConstantsOrderedDocuments;
import com.example.employeeworkplace.Models.Primary.*;
import com.example.employeeworkplace.Repositories.Primary.*;
import com.example.employeeworkplace.Repositories.Secondary.UserRepository;
import com.example.employeeworkplace.Services.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.VacationWithSalaryService;
import com.example.employeeworkplace.Services.VacationWithoutSalaryService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Disabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Сервис для генерации и сохранения различных типов документов.
 * <p>
 * Этот сервис отвечает за создание и сохранение документов различных типов, таких как SalaryOffset, VacationWithoutSalary,
 * VacationWithSalary, Certificate и TaxCertificate. При инициализации данных все существующие записи удаляются,
 * затем создаются новые документы с использованием различных репозиториев и сервисов.
 */
@Disabled
@ActiveProfiles("test")
@Service
public class DocumentGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentGeneratorService.class);

    private final SalaryOffsetRepository salaryOffsetRepository;
    private final VacationWithoutSalaryRepository vacationWithoutSalaryRepository;
    private final VacationWithSalaryRepository vacationWithSalaryRepository;
    private final DocumentNumberGeneratorService documentNumberGeneratorService;
    private final VacationWithoutSalaryService vacationWithoutSalaryService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final VacationWithSalaryService vacationWithSalaryService;
    private final TaxCertificateRepository taxCertificateRepository;
    private final CertificateRepository certificateRepository;

    @Autowired
    public DocumentGeneratorService(
            SalaryOffsetRepository salaryOffsetRepository,
            VacationWithoutSalaryRepository vacationWithoutSalaryRepository,
            VacationWithSalaryRepository vacationWithSalaryRepository,
            DocumentNumberGeneratorService documentNumberGeneratorService,
            VacationWithoutSalaryService vacationWithoutSalaryService,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            VacationWithSalaryService vacationWithSalaryService,
            TaxCertificateRepository taxCertificateRepository,
            CertificateRepository certificateRepository) {
        this.salaryOffsetRepository = salaryOffsetRepository;
        this.vacationWithoutSalaryRepository = vacationWithoutSalaryRepository;
        this.vacationWithSalaryRepository = vacationWithSalaryRepository;
        this.documentNumberGeneratorService = documentNumberGeneratorService;
        this.vacationWithoutSalaryService = vacationWithoutSalaryService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.vacationWithSalaryService = vacationWithSalaryService;
        this.taxCertificateRepository = taxCertificateRepository;
        this.certificateRepository = certificateRepository;
    }

    /**
     * Генерирует и сохраняет документы различных типов.
     * <p>
     * Метод удаляет все существующие записи из репозиториев, а затем создает и сохраняет новые записи
     * для всех типов документов: SalaryOffset, VacationWithoutSalary, VacationWithSalary, Certificate и TaxCertificate.
     * Также включены логи для отслеживания процесса генерации документов.
     */
    @Transactional
    public void generateAndSaveDocuments() {
        logger.info("Начинаем генерацию документов.");

        try {

            salaryOffsetRepository.deleteAll();
            vacationWithoutSalaryRepository.deleteAll();
            vacationWithSalaryRepository.deleteAll();
            certificateRepository.deleteAll();
            taxCertificateRepository.deleteAll();

            logger.info("Генерация документов SalaryOffset.");
            generateSalaryOffsets();


            logger.info("Генерация документов VacationWithoutSalary.");
            generateVacationWithoutSalaries();


            logger.info("Генерация документов VacationWithSalary.");
            generateVacationWithSalaries();


            logger.info("Генерация сертификатов.");
            generateCertificates();


            logger.info("Генерация налоговых сертификатов.");
            generateTaxCertificates();


        } catch (Exception e) {
            logger.error("Произошла ошибка при генерации документов: ", e);
            throw e;
        }

        logger.info("Генерация документов завершена.");
    }

    /**
     * Инициализирует данные при создании компонента.
     * <p>
     * Этот метод вызывается после создания компонента и выполняет генерацию и сохранение документов.
     */
    @PostConstruct
    public void initializeData() {
        generateAndSaveDocuments();
    }

    @Transactional
    protected void generateSalaryOffsets() {
        for (int i = 0; i < 15; i++) {
            SalaryOffset salaryOffset = new SalaryOffset();
            salaryOffset.setNameOfTheDocument(String.format("Salary Offset Document %d", i));
            salaryOffset.setOfficialPosition(String.format("Position %d", i));
            salaryOffset.setDateOfCreation(convertToSqlDate(LocalDate.now()));
            salaryOffset.setUserId(1L);

            String documentNumber = documentNumberGeneratorService.generateDocumentNumber("SO");
            salaryOffset.setDocumentNumber(documentNumber);
            BigDecimal sum = BigDecimal.valueOf(i + (i / 1.2));
            salaryOffset.setSumOfMoney(sum);

            salaryOffsetRepository.save(salaryOffset);
            logger.debug("Создан документ SalaryOffset: Название документа: '{}', Дата создания: '{}', Номер документа: '{}', Сумма: '{}",
                    salaryOffset.getNameOfTheDocument(),
                    salaryOffset.getDateOfCreation(),
                    salaryOffset.getDocumentNumber(),
                    salaryOffset.getSumOfMoney());
        }
    }

    @Transactional
    protected void generateVacationWithoutSalaries() {
        for (int i = 0; i < 10; i++) {
            VacationWithoutSalary vacationWithoutSalary = new VacationWithoutSalary();
            vacationWithoutSalary.setVacationStartDate(LocalDate.now());
            vacationWithoutSalary.setVacationEndDate(LocalDate.now().plusDays(7)); // Пример конца отпуска
            vacationWithoutSalary.setReason("Personal");
            vacationWithoutSalary.setApprovalStatus("Approved");
            vacationWithoutSalary.setUserId(1L);

            String documentNumber = documentNumberGeneratorService.generateDocumentNumber("VWS");
            vacationWithoutSalary.setDocumentNumber(documentNumber);

            vacationWithoutSalaryService.save(vacationWithoutSalary);

            logger.debug("Создан документ VacationWithoutSalary: Название документа: '{}', Дата начала отпуска: '{}', Дата окончания отпуска: '{}', Причина: '{}', Статус утверждения: '{}', Номер документа: '{}', ID пользователя: '{}'",
                    vacationWithoutSalary.getNameOfTheDocument(),
                    vacationWithoutSalary.getVacationStartDate(),
                    vacationWithoutSalary.getVacationEndDate(),
                    vacationWithoutSalary.getReason(),
                    vacationWithoutSalary.getApprovalStatus(),
                    vacationWithoutSalary.getDocumentNumber(),
                    vacationWithoutSalary.getUserId());
        }
    }

    @Transactional
    protected void generateVacationWithSalaries() {
        for (int i = 0; i < 10; i++) {
            VacationWithSalary vacationWithSalary = new VacationWithSalary();
            vacationWithSalary.setVacationStartDate(LocalDate.now());
            vacationWithSalary.setVacationEndDate(LocalDate.now().plusDays(7)); // Пример конца отпуска
            vacationWithSalary.setReason("Personal");
            vacationWithSalary.setApprovalStatus("Approved");
            vacationWithSalary.setUserId(1L);

            String documentNumber = documentNumberGeneratorService.generateDocumentNumber("VOS");
            vacationWithSalary.setDocumentNumber(documentNumber);

            vacationWithSalaryService.save(vacationWithSalary);

            logger.debug("Создан документ VacationWithSalary: Название документа: '{}', Дата начала отпуска: '{}', Дата окончания отпуска: '{}', Причина: '{}', Статус утверждения: '{}', Номер документа: '{}', ID пользователя: '{}'",
                    vacationWithSalary.getNameOfTheDocument(),
                    vacationWithSalary.getVacationStartDate(),
                    vacationWithSalary.getVacationEndDate(),
                    vacationWithSalary.getReason(),
                    vacationWithSalary.getApprovalStatus(),
                    vacationWithSalary.getDocumentNumber(),
                    vacationWithSalary.getUserId());
        }
    }

    @Transactional
    protected void generateCertificates() {
        for (int i = 0; i < 10; i++) {
            Certificate certificate = new Certificate();
            certificate.setUserId(1L);
            certificate.setNameOfTheCertificate("Certificate Name " + i);
            certificate.setTypeOfTheCertificate(ConstantsOrderedDocuments.Certificate);

            String documentNumber = documentNumberGeneratorService.generateDocumentNumber("CERT");
            certificate.setDocumentNumber(documentNumber);

            certificateRepository.save(certificate);

            logger.debug("Создан документ Certificate: Название сертификата: '{}', Тип сертификата: '{}', Номер документа: '{}'",
                    certificate.getNameOfTheCertificate(),
                    certificate.getTypeOfTheCertificate(),
                    certificate.getDocumentNumber());
        }
    }
    @Transactional
    protected void generateTaxCertificates() {
        for (int i = 0; i < 10; i++) {
            TaxCertificate taxCertificate = new TaxCertificate();
            taxCertificate.setUserId(1L);
            taxCertificate.setNameOfTheCertificate("Certificate Name " + i);
            taxCertificate.setTypeOfTheCertificate(ConstantsOrderedDocuments.TaxCertificate);

            String documentNumber = documentNumberGeneratorService.generateDocumentNumber("TXCRT");
            taxCertificate.setDocumentNumber(documentNumber);

            taxCertificateRepository.save(taxCertificate);

            logger.debug("Создан документ TaxCertificate: Название сертификата: '{}', Тип сертификата: '{}', Номер документа: '{}'",
                    taxCertificate.getNameOfTheCertificate(),
                    taxCertificate.getTypeOfTheCertificate(),
                    taxCertificate.getDocumentNumber());
        }
    }

    private Date convertToSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }
}
