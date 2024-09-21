package com.example.employeeworkplace.Services.DocumentServices;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Security.Authentication.CustomUserDetails;
import com.example.employeeworkplace.Services.UserServices.UserService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationDocumentationService {

    private final VacationDocumentationRepository vacationDocumentationRepository;
    private final UserService userService;


    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            log.info("Аутентификация найдена: {}",  authentication.getName());

            if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

                Long userId = userDetails.getId();
                log.info("ID текущего пользователя: {}", userId);

                return userId;
            } else {
                log.warn("Principal не является экземпляром CustomUserDetails");
            }
        } else {
            log.warn("Аутентификация отсутствует");
        }

        return null;
    }



    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            log.info("Аутентификация успешно получена.");

            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                log.info("Информация о пользователе успешно извлечена.");

                if (userDetails.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
                    GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
                    log.info("Роль пользователя: {}", authority.getAuthority());
                    return authority.getAuthority();
                } else {
                    log.warn("У пользователя отсутствуют назначенные роли.");
                }
            } else {
                log.warn("Principal не является экземпляром UserDetails.");
            }
        } else {
            log.warn("Аутентификация не получена.");
        }

        return null;
    }


    /**
     * Фильтрует список документов по отпуску, возвращая только те, которые относятся к текущему пользователю.
     *
     * @param documents   список всех документов
     * @return отфильтрованный список документов
     */

    public List<VacationDocumentation> filterByCurrentUser(List<VacationDocumentation> documents, Long idCurrentUser) {
        log.info("Получение списка документов по отпуску для пользователя с id: {}", idCurrentUser);


        UserDTO userDTO = userService.getUserDTOById(getCurrentUserId());
        if (userDTO == null) {
            log.error("Пользователь с id {} не найден", getCurrentUserId());
            return Collections.emptyList();
        }

        String currentUserName = userDTO.getFullName();
        log.info("Имя пользователя для id {}: {}", getCurrentUserId(), currentUserName);

        List<VacationDocumentation> filteredDocuments = documents.stream()
                .filter(doc -> doc.getUserId() != null && doc.getUserId().equals(getCurrentUserId()))
                .peek(doc -> {
                    doc.setUserFullName(currentUserName);
                    log.debug("Документ с id {} получил имя пользователя: {}", doc.getId(), currentUserName);
                })
                .collect(Collectors.toList());

        log.info("Отфильтровано {} документов для пользователя с id: {}", filteredDocuments.size(), getCurrentUserId());
        return filteredDocuments;
    }



    /**
     * Сохраняет или обновляет документ по отпуску в базе данных.
     *
     * @param document документ для сохранения или обновления
     * @return сохранённый документ
     */
    public VacationDocumentation save(VacationDocumentation document) {
        log.info("Сохранение или обновление документа по отпуску с id: {}", document.getId());
        VacationDocumentation savedDocument = vacationDocumentationRepository.save(document);
        log.debug("Документ по отпуску с id {} успешно сохранён", savedDocument.getId());
        return savedDocument;
    }

    /**
     * Возвращает список всех документов по отпуску с детализированной информацией о пользователях.
     *
     * @return список документов с обновлёнными данными о пользователях
     */

    public List<VacationDocumentation> findAllWithUserDetails() {
        log.info("Получение всех документов по отпуску с деталями пользователей из базы данных");

        List<VacationDocumentation> documents = vacationDocumentationRepository.findAll();
        log.debug("Получено {} документов по отпуску из базы данных", documents.size());

        log.info("Возвращаем {} документов по отпуску с обновленными данными о пользователях", documents.size());
        return documents;
    }
}
