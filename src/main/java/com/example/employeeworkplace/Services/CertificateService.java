package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Models.Primary.Certificate;
import com.example.employeeworkplace.Repositories.Primary.CertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с сертификатами.
 * Предоставляет методы для добавления, обновления, удаления и получения сертификатов.
 */
@Slf4j
@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    /**
     * Конструктор для инъекции зависимости {@link CertificateRepository}.
     *
     * @param certificateRepository репозиторий для работы с сертификатами
     */
    @Autowired
    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
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
     * Удаляет сертификат по его ID.
     *
     * @param id ID сертификата для удаления
     */
    public void deleteCertificate(Long id) {
        log.debug("Удаление сертификата с ID {}", id);
        certificateRepository.deleteById(id);
    }

    /**
     * Находит сертификаты по заданным фильтрам.
     *
     * @param name      название сертификата
     * @param startDate начальная дата
     * @param endDate   конечная дата
     * @param userId    ID пользователя
     * @param hasFile   наличие файла (true/false)
     * @return список сертификатов, соответствующих фильтрам
     */
    public List<Certificate> filterCertificates(String name, Date startDate, Date endDate, Long userId, Boolean hasFile) {
        log.debug("Фильтрация сертификатов с фильтрами - название: {}, дата начала: {}, дата конца: {}, ID пользователя: {}, наличие файла: {}",
                name, startDate, endDate, userId, hasFile);
        return certificateRepository.findWithFilters(name, startDate, endDate, userId, hasFile);
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
}
