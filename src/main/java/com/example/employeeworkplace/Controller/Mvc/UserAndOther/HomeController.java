package com.example.employeeworkplace.Controller.Mvc.UserAndOther;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Контроллер для обработки запросов на корневой URL.
 * <p>
 * Этот контроллер перенаправляет запросы с корневого URL на /profile/.
 * </p>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {


        /**
     * Перенаправляет запросы с корневого URL на /profile/.
     * <p>
     * Метод обрабатывает запросы на корневой URL и перенаправляет их на /profile/.
     * </p>
     *
     * @return перенаправление на /profile/
     */
    @GetMapping("/")
    public RedirectView home() {
        log.debug("Перенаправление с корневого URL на /profile/");
        return new RedirectView("/profile/");
    }
}
