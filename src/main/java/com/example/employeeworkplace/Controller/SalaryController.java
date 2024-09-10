package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.SalaryOffsetService;
import com.example.employeeworkplace.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Контроллер для работы с зарплатами.
 */
@Controller
@RequiredArgsConstructor
public class SalaryController {

    private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);

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
        logger.debug("Запрос на страницу зарплат для пользователя: {}", username);

        User currentUser = userService.getUserByUsername(username);
        if (currentUser == null) {
            logger.error("Пользователь не найден: {}", username);
            return "error";
        }

        List<SalaryOffset> salaryOffsets = salaryOffsetService.filterByCurrentUser(salaryOffsetService.listSalaryOffset(), currentUser);
        model.addAttribute("SalaryOffsets", salaryOffsets);
        model.addAttribute("user", currentUser);
        model.addAttribute("userRoles", currentUser.getRole());

        logger.debug("Данные зарплат успешно загружены для пользователя: {}", username);
        return "Salary";
    }
}
