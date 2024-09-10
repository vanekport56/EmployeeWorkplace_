package com.example.employeeworkplace.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.employeeworkplace.Models.Primary.VacationDocumentation;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Repositories.Primary.VacationDocumentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacationDocumentationService {

    private final VacationDocumentationRepository vacationDocumentationRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(VacationDocumentationService.class);

    /**
     * Фильтрует список документов по отпуску, возвращая только те, которые относятся к текущему пользователю.
     *
     * @param documents   список всех документов
     * @param currentUser текущий пользователь
     * @return отфильтрованный список документов
     */
    @Cacheable(value = "vacationDocuments", key = "#currentUser.id")
    public List<VacationDocumentation> filterByCurrentUser(List<VacationDocumentation> documents, User currentUser) {
        logger.info("Получение списка документов по отпуску для пользователя с id: {}", currentUser.getId());

        return documents.stream()
                .filter(doc -> doc.getUserId() != null
                        && doc.getUserId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Сохраняет или обновляет документ по отпуску в базе данных.
     *
     * @param document документ для сохранения или обновления
     * @return сохранённый документ
     */
    public VacationDocumentation save(VacationDocumentation document) {
        logger.info("Сохранение или обновление документа по отпуску с id: {}", document.getId());
        VacationDocumentation savedDocument = vacationDocumentationRepository.save(document);
        logger.debug("Документ по отпуску с id {} успешно сохранён", savedDocument.getId());
        return savedDocument;
    }

    /**
     * Возвращает список всех документов по отпуску с детализированной информацией о пользователях.
     *
     * @return список документов с обновлёнными данными о пользователях
     */
    @Cacheable("vacationDocumentsWithUsers")
    public List<VacationDocumentation> findAllWithUserDetails() {
        logger.info("Получение всех документов по отпуску с деталями пользователей из базы данных");

        List<VacationDocumentation> documents = vacationDocumentationRepository.findAll();
        logger.debug("Получено {} документов по отпуску из базы данных", documents.size());

        // Обновление информации о пользователях для каждого документа
        for (VacationDocumentation doc : documents) {
            if (doc.getUserId() != null) {
                User user = userService.getUserById(doc.getUserId());
                doc.setTransientUser(user);
                logger.debug("Обновлены данные пользователя для документа с ID: {}", doc.getId());
            }
        }

        logger.info("Возвращаем {} документов по отпуску с обновленными данными о пользователях", documents.size());
        return documents;
    }
}
