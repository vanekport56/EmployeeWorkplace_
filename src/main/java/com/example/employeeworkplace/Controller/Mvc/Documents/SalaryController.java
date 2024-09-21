package com.example.employeeworkplace.Controller.Mvc.Documents;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.SalaryAndCertificateService.SalaryOffsetService;
import com.example.employeeworkplace.Services.UserServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Контроллер для работы с зарплатами.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class SalaryController {


    private final SalaryOffsetService salaryOffsetService;
    private final UserService userService;

    /**
     * Отображает страницу с зарплатами текущего пользователя.
     *
     * @param model объект модели для передачи данных в представление
     * @param authentication объект аутентификации текущего пользователя
     * @return имя шаблона для страницы зарплат
     */
    @GetMapping("/salary/")
    public String salary(Model model, Authentication authentication) {
        String username = authentication.getName();
        log.debug("Запрос на страницу зарплат для пользователя: {}", username);

        User currentUser = userService.getUserByUsername(username);
        if (currentUser == null) {
            log.error("Пользователь не найден: {}", username);
            return "Error";
        }

        List<SalaryOffset> salaryOffsets = salaryOffsetService.filterByCurrentUser(salaryOffsetService.listSalaryOffset(), currentUser);
        model.addAttribute("SalaryOffsets", salaryOffsets);
        model.addAttribute("user", currentUser);
        model.addAttribute("userRoles", currentUser.getRole());

        log.debug("Данные зарплат успешно загружены для пользователя: {}", username);
        return "ContentTemplates/Salary";
    }
}
