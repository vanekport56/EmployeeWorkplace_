package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.CertificateType;
import com.example.employeeworkplace.Models.Primary.Certificate;
import com.example.employeeworkplace.Models.Primary.OrderedDocuments;
import com.example.employeeworkplace.Models.Primary.TaxCertificate;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Primary.CertificateRepository;
import com.example.employeeworkplace.Repositories.Primary.OrderedDocumentsRepository;
import com.example.employeeworkplace.Repositories.Primary.TaxCertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
     * Добавляет новый сертификат в хранилище.
     *
     * @param certificate сертификат для сохранения
     * @return сохраненный сертификат
     */
    public Certificate saveCertificate(Certificate certificate) {
        log.debug("Сохранение сертификата: {}", certificate);
        return certificateRepository.save(certificate);
    }

    /**
     * Обновляет существующий сертификат по его ID.
     *
     * @param id              ID сертификата для обновления
     * @param updatedCertificate объект с новыми данными сертификата
     * @return обновленный сертификат
     * @throws RuntimeException если сертификат с указанным ID не найден
     */
    public Certificate updateCertificate(Long id, Certificate updatedCertificate) {
        log.debug("Обновление сертификата с ID {}: {}", id, updatedCertificate);
        Optional<Certificate> optionalCertificate = certificateRepository.findById(id);
        if (optionalCertificate.isPresent()) {
            Certificate certificate = optionalCertificate.get();
            certificate.setNameOfTheCertificate(updatedCertificate.getNameOfTheCertificate());
            certificate.setDateOfCreation(updatedCertificate.getDateOfCreation());
            certificate.setFile(updatedCertificate.getFile());
            certificate.setUserId(updatedCertificate.getUserId());
            return certificateRepository.save(certificate);
        } else {
            log.error("Сертификат с ID {} не найден", id);
            throw new RuntimeException("Сертификат с ID " + id + " не найден.");
        }
    }
    /**
     * Получает уникальные типы сертификатов.
     *
     * @return список уникальных типов сертификатов
     */
    public List<String> getCertificateTypes() {
        log.debug("Получение уникальных типов сертификатов");
        return Arrays.stream(CertificateType.values())
                .map(CertificateType::getDisplayName)
                .collect(Collectors.toList());
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
     * Удаляет сертификат по его ID.
     *
     * @param id ID сертификата для удаления
     */
    public void deleteCertificate(Long id) {
        log.debug("Удаление сертификата с ID {}", id);
        certificateRepository.deleteById(id);
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

    /**
     * Находит сертификат по его ID.
     *
     * @param id ID сертификата
     * @return найденный сертификат
     * @throws RuntimeException если сертификат с указанным ID не найден
     */
    public Certificate getCertificateById(Long id) {
        log.debug("Поиск сертификата с ID {}", id);
        return certificateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Сертификат с ID {} не найден", id);
                    return new RuntimeException("Сертификат с ID " + id + " не найден.");
                });

    }
    /**
     * Получает текущего аутентифицированного пользователя.
     *
     * @return ID текущего пользователя
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long) {
            return (Long) authentication.getPrincipal();
        }
        throw new RuntimeException("Не удалось получить ID текущего пользователя");
    }
    /**
     * Находит сертификаты по заданным фильтрам с учётом текущего пользователя.
     *
     * @param name      название сертификата (может быть {@code null}).
     * @param startDate начальная дата (может быть {@code null}).
     * @param endDate   конечная дата (может быть {@code null}).
     * @return список сертификатов, соответствующих фильтрам
     */


    public List<TaxCertificate> filterTaxCertificatesForUser(String name, LocalDate startDate, LocalDate endDate, User currentUser) {

        Long userId = currentUser.getId(); // Получаем ID текущего пользователя
        log.debug("Фильтрация налоговых сертификатов по текущему пользователю  с фильтрами - название: {}, дата начала: {}, дата конца: {}, ID пользователя: {}",
                name, startDate, endDate, userId);
        return taxCertificateRepository.findWithFilters(name,userId);
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
    public OrderedDocuments saveOrderedDocument(OrderedDocuments orderedDocuments) {
        log.debug("Сохранение сертификата: {}", orderedDocuments);
        return orderedDocumentsRepository.save(orderedDocuments);
    }


}

