package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов на панель управления.
 * <p>
 * Этот контроллер отвечает за отображение панели управления для администраторов.
 * </p>
 */
@Controller
@RequiredArgsConstructor
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    private final UserService userService;

    /**
     * Отображает панель управления для администраторов.
     * <p>
     * Этот метод получает информацию о текущем пользователе и передает данные в модель.
     * </p>
     *
     * @param authentication объект для получения информации о текущем пользователе
     * @param model модель, в которую добавляются атрибуты для отображения
     * @return имя шаблона для отображения панели управления
     */
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Authentication authentication, Model model) {
        log.debug("Получение информации о текущем пользователе для панели управления");

        User currentUser = userService.getUserByUsername(authentication.getName());
        model.addAttribute("userRoles", currentUser.getRole());

        log.debug("Отображение панели управления для пользователя с ролью: {}", currentUser.getRole());
        return "admin/dashboard";
    }
}
