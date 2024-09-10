package com.example.employeeworkplace.Security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.employeeworkplace.Services.UserService;

import java.io.IOException;

@Component
public class ProfileCompletionFilter implements Filter {

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация фильтра, если требуется
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Получаем имя пользователя из сессии или контекста безопасности
        String username = httpRequest.getUserPrincipal() != null ? httpRequest.getUserPrincipal().getName() : null;

        if (username != null) {
            // Если профиль неполный, перенаправляем на страницу заполнения
            boolean isProfileIncomplete = userService.isProfileIncomplete(username);
            if (isProfileIncomplete && !httpRequest.getRequestURI().startsWith("/profile/incomplete")) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/profile/incomplete");
                return;
            }
        }

        // Продолжаем цепочку фильтров
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Очистка ресурсов, если требуется
    }
}
