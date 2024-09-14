package com.example.employeeworkplace.Security;

import com.example.employeeworkplace.Models.Secondary.Role;
import com.example.employeeworkplace.Models.Secondary.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Аспект для проверки доступа на основе аннотации {@link Secured}.
 * Выполняет проверку прав доступа пользователя перед выполнением метода,
 * аннотированного этой аннотацией.
 */
@Aspect
@Component
public class SecurityAspect {

    private static final Logger log = LoggerFactory.getLogger(SecurityAspect.class);

    /**
     * Определяет точку доступа для методов, аннотированных {@link Secured}.
     */
    @Pointcut("@annotation(com.example.employeeworkplace.Security.Secured)")
    public void securedMethod() {
        // Пункт назначения для аннотации Secured
    }

    /**
     * Проверяет доступ пользователя перед выполнением метода,
     * аннотированного аннотацией {@link Secured}.
     *
     * @param secured аннотация {@link Secured}, примененная к методу
     */
    @Before("securedMethod() && @annotation(secured)")
    public void checkAccess(Secured secured) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(userDetails instanceof User)) {
                log.error("Неправильный тип объекта UserDetails. Ожидался объект типа User, но получен: {}", userDetails.getClass().getName());
                throw new SecurityException("User details type mismatch");
            }

            User currentUser = (User) userDetails;

            if (!hasPermission(currentUser)) {
                log.warn("Доступ запрещен для пользователя: {} с ролью: {}", currentUser.getUsername(), currentUser.getRole());
                throw new SecurityException("Access denied");
            }
        } catch (Exception e) {
            log.error("Ошибка проверки доступа: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Проверяет, имеет ли текущий пользователь необходимые права доступа.
     *
     * @param currentUser текущий пользователь
     * @return {@code true}, если у пользователя есть доступ, {@code false} в противном случае
     */
    private boolean hasPermission(User currentUser) {
        boolean hasPermission = currentUser.getRole().equals("ROLE_" + Role.admin);

        if (!hasPermission) {
            log.debug("Пользователь {} не имеет необходимого доступа. Требуется роль: ROLE_{}", currentUser.getUsername(), Role.admin);
        }

        return hasPermission;
    }
}
