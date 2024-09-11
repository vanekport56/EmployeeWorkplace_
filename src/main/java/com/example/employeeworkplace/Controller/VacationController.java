package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Models.Primary.VacationDocumentation;
import com.example.employeeworkplace.Models.Primary.VacationWithSalary;
import com.example.employeeworkplace.Models.Primary.VacationWithoutSalary;
import com.example.employeeworkplace.Services.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.VacationDocumentationService;
import com.example.employeeworkplace.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.employeeworkplace.Utils.DateUtils;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Контроллер для управления запросами, связанными с отпускными документами.
 */
@Controller
@RequiredArgsConstructor
public class VacationController {

    private final VacationDocumentationService vacationDocumentationService;
    private final UserService userService;
    private final DocumentNumberGeneratorService documentNumberGeneratorService;
    private final ConcurrentMapCacheManager cacheManager;
    private static final Logger logger = LoggerFactory.getLogger(VacationController.class);

    /**
     * Отображает страницу со списком отпускных документов пользователя.
     *
     * @param page          Номер страницы для пагинации
     * @param size          Размер страницы для пагинации
     * @param sortDirection Направление сортировки (asc/desc)
     * @param model         Модель для передачи данных в представление
     * @param authentication Информация о текущем пользователе
     * @return Название шаблона для страницы с документами отпусков
     */
    @GetMapping("/vacation/")
    public String vacation(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDirection,
            Model model, Authentication authentication, User currentUser) {
        Long idcurrentUser = vacationDocumentationService.getCurrentUserId();

        List<VacationDocumentation> vacationDocuments = vacationDocumentationService.filterByCurrentUser(
                vacationDocumentationService.findAllWithUserDetails(), idcurrentUser);
        if (vacationDocuments != null && !vacationDocuments.isEmpty()) {
            logger.info("Количество документов отпуска для текущего пользователя: {}", vacationDocuments.size());
            for (VacationDocumentation doc : vacationDocuments) {
                logger.debug("Документ отпуска: {}", doc);
            }
        } else {
            logger.info("Не найдено документов отпуска для текущего пользователя.");
        }

//        vacationDocuments = vacationDocumentationService.findAllWithUserDetails();

        Cache cache = cacheManager.getCache("vacationDocuments");
        if (cache != null && cache.get(vacationDocuments) != null) {
            logger.info("Получение списка документов по отпуску из кэша для пользователя с id: {}",idcurrentUser);
        } else {
            logger.info("Получение списка документов по отпуску с сервера для пользователя с id: {}", idcurrentUser);
        }

        List<VacationDocumentation> formattedVacationDocuments = vacationDocuments.stream()
                .peek(doc -> {
                    String formattedStartDate = doc.getVacationStartDate() != null
                            ? doc.getVacationStartDate().format(DateUtils.DateToString)
                            : "";
                    String formattedEndDate = doc.getVacationEndDate() != null
                            ? doc.getVacationEndDate().format(DateUtils.DateToString)
                            : "";
                    doc.setFormattedVacationStartDate(formattedStartDate);
                    doc.setFormattedVacationEndDate(formattedEndDate);
                })
                .toList();

        Comparator<VacationDocumentation> comparator = Comparator.comparing(VacationDocumentation::getVacationStartDate);
        if ("desc".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }
        List<VacationDocumentation> sortedVacationDocuments = formattedVacationDocuments.stream()
                .sorted(comparator)
                .toList();

        int start = Math.min(page * size, sortedVacationDocuments.size());
        int end = Math.min(start + size, sortedVacationDocuments.size());
        List<VacationDocumentation> paginatedDocuments = sortedVacationDocuments.subList(start, end);

        int totalPages = (int) Math.ceil((double) sortedVacationDocuments.size() / size);

        boolean hasMoreData = (page + 1) * size < sortedVacationDocuments.size();



        String role  = String.valueOf(vacationDocumentationService.getCurrentUserRole());
        logger.info("Роль текущего пользователя: {}", role);
        model.addAttribute("userRoles", role);
        model.addAttribute("hasMoreData", hasMoreData);
        model.addAttribute("vacationDocuments", paginatedDocuments);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("sortDirection", sortDirection);


        return "Vacation";
    }

    /**
     * Отображает форму для создания нового отпускного документа.
     *
     * @param model Модель для передачи данных в представление
     * @return Название шаблона для формы создания отпуска
     */
    @GetMapping("/vacation/create")
    public String showCreateVacationForm(Model model) {
        model.addAttribute("vacationTypes", List.of("WITH_SALARY", "WITHOUT_SALARY"));
        return "vacationCreate";
    }

    /**
     * Обрабатывает запрос на создание нового отпускного документа.
     *
     * @param reason            Причина отпуска
     * @param vacationStartDate Дата начала отпуска
     * @param vacationEndDate   Дата окончания отпуска
     * @param vacationType      Тип отпуска (с оплатой/без оплаты)
     * @param authentication    Информация о текущем пользователе
     * @return Перенаправление на страницу со списком отпусков
     */
    @PostMapping("/vacation/create")
    public String createVacation(
            @RequestParam String reason,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vacationStartDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vacationEndDate,
            @RequestParam String vacationType,
            Authentication authentication) {

        User currentUser = userService.getUserByUsername(authentication.getName());

        VacationDocumentation newDocument;
        if ("WITH_SALARY".equals(vacationType)) {
            newDocument = new VacationWithSalary();
            newDocument.setIsWithSalary(true);
        } else if ("WITHOUT_SALARY".equals(vacationType)) {
            newDocument = new VacationWithoutSalary();
            newDocument.setIsWithSalary(false);
        } else {
            throw new IllegalArgumentException("Invalid vacation type: " + vacationType);
        }

        newDocument.setNameOfTheDocument(""); // не хочу писать метод без аргументов при присутствии аннотации @Data
        newDocument.setReason(reason);
        newDocument.setVacationStartDate(vacationStartDate);
        newDocument.setVacationEndDate(vacationEndDate);
        newDocument.setApprovalStatus("На рассмотрении");
        newDocument.setUserId(currentUser.getId());

        String documentNumber = documentNumberGeneratorService.generateDocumentNumber(
                vacationType.equals("WITH_SALARY") ? "VWS" : "VWO");
        newDocument.setDocumentNumber(documentNumber);

        try {
            vacationDocumentationService.save(newDocument);
        } catch (Exception e) {
            logger.error("Ошибка при сохранении документа: ", e);
            return "error";
        }
        return "redirect:/vacation/";
    }
}
