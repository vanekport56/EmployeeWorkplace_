package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Primary.SalaryOffsetRepository;
import com.example.employeeworkplace.Services.SalaryAndCertificateService.SalaryOffsetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интеграционные тесты для сервиса работы с документами SalaryOffset {@link SalaryOffsetService}.
 * Проверяют функциональность фильтрации, получения, сохранения, обновления и удаления документов.
 */
@SpringBootTest
@Transactional
public class SalaryOffsetServiceIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(SalaryOffsetServiceIntegrationTest.class);

    @Autowired
    private SalaryOffsetService salaryOffsetService;

    @Autowired
    private SalaryOffsetRepository salaryOffsetRepository;

    private User currentUser;

    @BeforeEach
    void setUp() {
        salaryOffsetRepository.deleteAll();

        // Создание тестового пользователя
        currentUser = new User();
        currentUser.setId(1L);
        logger.debug("Тестовый пользователь создан: {}", currentUser);

        // Создание тестовых документов
        SalaryOffset doc1 = new SalaryOffset();
        doc1.setUserId(1L);
        salaryOffsetRepository.save(doc1);

        SalaryOffset doc2 = new SalaryOffset();
        doc2.setUserId(2L);
        salaryOffsetRepository.save(doc2);
    }

    /**
     * Тестирует фильтрацию документов по текущему пользователю.
     */
    @Test
    public void testFilterByCurrentUser() {
        List<SalaryOffset> documents = salaryOffsetRepository.findAll();
        List<SalaryOffset> filteredDocuments = salaryOffsetService.filterByCurrentUser(documents, currentUser);

        logger.debug("Отфильтрованные документы: {}", filteredDocuments);
        assertThat(filteredDocuments).hasSize(1);
        assertThat(filteredDocuments.get(0).getUserId()).isEqualTo(currentUser.getId());
    }

    /**
     * Тестирует получение списка всех документов SalaryOffset.
     */
    @Test
    public void testListSalaryOffset() {
        List<SalaryOffset> documents = salaryOffsetService.listSalaryOffset();

        logger.debug("Список всех документов SalaryOffset: {}", documents);
        assertThat(documents).hasSize(2);
    }

    /**
     * Тестирует получение документа SalaryOffset по ID.
     */
    @Test
    public void testGetSalaryOffsetById() {
        SalaryOffset savedDoc = salaryOffsetRepository.findAll().get(0);
        SalaryOffset foundDoc = salaryOffsetService.getSalaryOffsetById(savedDoc.getId());

        logger.debug("Найденный документ по ID: {}", foundDoc);
        assertThat(foundDoc).isNotNull();
        assertThat(foundDoc.getId()).isEqualTo(savedDoc.getId());
    }

    /**
     * Тестирует сохранение нового документа SalaryOffset.
     */
    @Test
    public void testSaveSalaryOffset() {
        SalaryOffset newDoc = new SalaryOffset();
        newDoc.setUserId(3L);
        SalaryOffset savedDoc = salaryOffsetService.saveSalaryOffset(newDoc);

        logger.debug("Сохраненный документ: {}", savedDoc);
        assertThat(savedDoc).isNotNull();
        assertThat(savedDoc.getUserId()).isEqualTo(3L);
    }

    /**
     * Тестирует обновление существующего документа SalaryOffset.
     */
    @Test
    public void testUpdateSalaryOffset() {
        SalaryOffset docToUpdate = salaryOffsetRepository.findAll().get(0);
        docToUpdate.setUserId(4L);
        SalaryOffset updatedDoc = salaryOffsetService.updateSalaryOffset(docToUpdate.getId(), docToUpdate);

        logger.debug("Обновленный документ: {}", updatedDoc);
        assertThat(updatedDoc).isNotNull();
        assertThat(updatedDoc.getUserId()).isEqualTo(4L);
    }

    /**
     * Тестирует удаление документа SalaryOffset по ID.
     */
    @Test
    public void testDeleteSalaryOffset() {
        SalaryOffset docToDelete = salaryOffsetRepository.findAll().get(0);
        salaryOffsetService.deleteSalaryOffset(docToDelete.getId());

        logger.debug("Документ удален с ID: {}", docToDelete.getId());
        assertThat(salaryOffsetRepository.findById(docToDelete.getId())).isNotPresent();
    }
}
