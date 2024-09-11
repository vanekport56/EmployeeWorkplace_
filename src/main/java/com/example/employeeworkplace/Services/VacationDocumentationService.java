package com.example.employeeworkplace.Services;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.employeeworkplace.Models.Primary.VacationDocumentation;
import com.example.employeeworkplace.Repositories.Primary.VacationDocumentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacationDocumentationService {

    private final VacationDocumentationRepository vacationDocumentationRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(VacationDocumentationService.class);

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            logger.info("Аутентификация найдена: " + authentication.getName());

            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                Long userId = userDetails.getId();
                logger.info("ID текущего пользователя: " + userId);

                return userId;
            } else {
                logger.warn("Principal не является экземпляром CustomUserDetails");
            }
        } else {
            logger.warn("Аутентификация отсутствует");
        }

        return null; // Если пользователь не аутентифицирован
    }



    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("Аутентификация успешно получена.");

            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                System.out.println("Информация о пользователе успешно извлечена.");

                if (userDetails.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
                    GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
                    System.out.println("Роль пользователя: " + authority.getAuthority());
                    return authority.getAuthority();
                } else {
                    System.out.println("У пользователя отсутствуют назначенные роли.");
                }
            } else {
                System.out.println("Principal не является экземпляром UserDetails.");
            }
        } else {
            System.out.println("Аутентификация не получена.");
        }

        return null;
    }


    /**
     * Фильтрует список документов по отпуску, возвращая только те, которые относятся к текущему пользователю.
     *
     * @param documents   список всех документов
     * @return отфильтрованный список документов
     */
//    public Long one1 = 1L;
    public List<VacationDocumentation> filterByCurrentUser(List<VacationDocumentation> documents, Long idCurrentUser) {
        logger.info("Получение списка документов по отпуску для пользователя с id: {}", getCurrentUserId());

        // Получаем имя текущего пользователя один раз
        UserDTO userDTO = userService.getUserDTOById(getCurrentUserId());
        if (userDTO == null) {
            logger.error("Пользователь с id {} не найден", getCurrentUserId());
            return Collections.emptyList();
        }

        String currentUserName = userDTO.getFullName();
        logger.info("Имя пользователя для id {}: {}", getCurrentUserId(), currentUserName);

        List<VacationDocumentation> filteredDocuments = documents.stream()
                .filter(doc -> doc.getUserId() != null && doc.getUserId().equals(getCurrentUserId()))
                .peek(doc -> {
                    doc.setUserFullName(currentUserName);
                    logger.debug("Документ с id {} получил имя пользователя: {}", doc.getId(), currentUserName);
                })
                .collect(Collectors.toList());

        logger.info("Отфильтровано {} документов для пользователя с id: {}", filteredDocuments.size(), getCurrentUserId());
        return filteredDocuments;
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

    public List<VacationDocumentation> findAllWithUserDetails() {
        logger.info("Получение всех документов по отпуску с деталями пользователей из базы данных");

        List<VacationDocumentation> documents = vacationDocumentationRepository.findAll();
        logger.debug("Получено {} документов по отпуску из базы данных", documents.size());

        logger.info("Возвращаем {} документов по отпуску с обновленными данными о пользователях", documents.size());
        return documents;
    }
}
