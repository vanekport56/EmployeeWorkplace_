package com.example.employeeworkplace.Security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.employeeworkplace.Services.UserService;

import java.io.IOException;

/**
 * Фильтр для проверки полноты профиля пользователя.
 * Перенаправляет пользователя на страницу с запросом заполнения профиля, если он неполный.
 */
@Component
@Slf4j
public class ProfileCompletionFilter implements Filter {

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String username = httpRequest.getUserPrincipal() != null ? httpRequest.getUserPrincipal().getName() : null;

        if (username != null) {
            boolean isProfileIncomplete = userService.isProfileIncomplete(username);
            if (isProfileIncomplete && !httpRequest.getRequestURI().startsWith("/profile/incomplete")) {
                log.info("Профиль пользователя {} неполный. Перенаправление на страницу заполнения профиля.", username);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/profile/incomplete");
                return;
            }
        }


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Очистка ресурсов, если требуется
    }
}
