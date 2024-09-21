package com.example.employeeworkplace.Services.SalaryAndCertificateService;

import com.example.employeeworkplace.Models.ConstantsAndEnums.CertificateType;
import com.example.employeeworkplace.Models.Primary.Certificate;
import com.example.employeeworkplace.Models.Primary.OrderedDocuments;
import com.example.employeeworkplace.Models.Primary.TaxCertificate;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Primary.CertificateRepository;
import com.example.employeeworkplace.Repositories.Primary.OrderedDocumentsRepository;
import com.example.employeeworkplace.Repositories.Primary.TaxCertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для работы с сертификатами.
 * Предоставляет методы для добавления, обновления, удаления и получения сертификатов.
 */
@Slf4j
@Service
public class CertificateService {
    private final TaxCertificateRepository taxCertificateRepository;
    private final CertificateRepository certificateRepository;
    private final OrderedDocumentsRepository orderedDocumentsRepository;


    /**
     * Конструктор для инъекции зависимости {@link CertificateRepository}.
     *
     * @param certificateRepository репозиторий для работы с сертификатами
     */
    @Autowired
    public CertificateService(CertificateRepository certificateRepository,
                              TaxCertificateRepository taxCertificateRepository, OrderedDocumentsRepository orderedDocumentsRepository) {
        this.certificateRepository = certificateRepository;
        this.taxCertificateRepository = taxCertificateRepository;
        this.orderedDocumentsRepository = orderedDocumentsRepository;
    }



    /**
     * Получает названия сертификатов по типу.
     *
     * @return карта типов сертификатов и соответствующих названий
     */
    public Map<String, List<String>> getCertificateNames() {
        log.debug("Получение названий сертификатов по типу");
        return Arrays.stream(CertificateType.values())
                .collect(Collectors.toMap(
                        CertificateType::getDisplayName,
                        CertificateType::getPossibleNames
                ));
    }

    /**
     * Получает все сертификаты.
     *
     * @return список всех сертификатов
     */
    public List<Certificate> getAllCertificates() {
        log.debug("Получение всех сертификатов");
        return certificateRepository.findAll();
    }
    /**
     * Получает все сертификаты.
     *
     * @return список всех сертификатов
     */
    public List<TaxCertificate> getAllTaxCertificates() {
        log.debug("Получение всех налоговых сертификатов");
        return taxCertificateRepository.findAll();
    }


    public List<OrderedDocuments> getAllFilterCertificates(String name, User currentUser) {
        log.debug("Получение всех сертификатов и налоговых сертификатов");

        List<Certificate> certificates = certificateRepository.findWithFilters(name,currentUser.getId());
        List<TaxCertificate> taxCertificates = taxCertificateRepository.findWithFilters(name, currentUser.getId());


        List<OrderedDocuments> allDocuments = new ArrayList<>();
        allDocuments.addAll(certificates);
        allDocuments.addAll(taxCertificates);

        return allDocuments;
    }
    public void saveOrderedDocument(OrderedDocuments orderedDocuments) {
        log.debug("Сохранение сертификата: {}", orderedDocuments);
        orderedDocumentsRepository.save(orderedDocuments);
    }


}

