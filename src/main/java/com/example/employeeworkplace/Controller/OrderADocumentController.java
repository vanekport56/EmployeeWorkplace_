package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов на страницу заказа документа.
 * <p>
 * Этот контроллер обрабатывает запросы на страницу заказа документа и добавляет информацию
 * о ролях пользователя в модель.
 * </p>
 */
@Controller
@RequiredArgsConstructor
public class OrderADocumentController {

    private static final Logger log = LoggerFactory.getLogger(OrderADocumentController.class);

    private final UserService userService;
    /**
     * Отображает страницу заказа документа.
     * <p>
     * Этот метод обрабатывает запросы на /orderadocument/ и добавляет информацию о ролях пользователя
     * в модель перед отображением страницы.
     * </p>
     *
     * @param authentication объект аутентификации текущего пользователя
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона для страницы заказа документа
     */
    @GetMapping("/orderadocument/")
    public String orderADocument(Authentication authentication, Model model) {
        log.debug("Получение информации о пользователе для страницы заказа документа");

        User currentUser = userService.getUserByUsername(authentication.getName());
        model.addAttribute("userRoles", currentUser.getRole());

        return "OrderADocument";
    }
}
