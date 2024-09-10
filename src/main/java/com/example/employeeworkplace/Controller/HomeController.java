package com.example.employeeworkplace.Controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Контроллер для обработки запросов на корневой URL.
 * <p>
 * Этот контроллер перенаправляет запросы с корневого URL на /profile/.
 * </p>
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
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
