package com.example.employeeworkplace.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * Обработчик неудачных попыток аутентификации.
 * <p>Этот класс обрабатывает ошибки аутентификации, логирует их и перенаправляет пользователя на страницу логина с параметром ошибки.</p>
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    /**
     * Обрабатывает ошибку аутентификации.
     *
     * @param request   HTTP запрос, содержащий информацию о попытке аутентификации.
     * @param response  HTTP ответ, который будет отправлен клиенту.
     * @param exception Исключение, содержащее информацию об ошибке аутентификации.
     * @throws IOException Если происходит ошибка ввода-вывода.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        logger.error("Ошибка аутентификации: {}", exception.getMessage());


        String username = request.getParameter("username");
        logger.info("Неудачная попытка входа с именем пользователя: {}", username);


        String errorMessage = URLEncoder.encode("Не верные данные", StandardCharsets.UTF_8.toString());

        logger.info("Неудачная попытка входа в систему с использованием имени пользователя:"+ username);

        response.sendRedirect("/login?error=" + errorMessage);
    }
}
