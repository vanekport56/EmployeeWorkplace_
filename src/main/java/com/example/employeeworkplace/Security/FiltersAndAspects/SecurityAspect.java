package com.example.employeeworkplace.Security.FiltersAndAspects;


import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Security.Configuration.Secured;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Аспект для проверки доступа на основе аннотации {@link Secured}.
 * Выполняет проверку прав доступа пользователя перед выполнением метода,
 * аннотированного этой аннотацией.
 */
@Slf4j
@Aspect
@Component
public class SecurityAspect {

    /**
     * Определяет точку доступа для методов, аннотированных {@link Secured}.
     */
    @Pointcut("@annotation(com.example.employeeworkplace.Security.Configuration.Secured)")
    public void securedMethod() {
    }

    /**
     * Проверяет доступ пользователя перед выполнением метода,
     * аннотированного аннотацией {@link Secured}.
     *
     * @param secured аннотация {@link Secured}, примененная к методу
     */
    @Before("securedMethod() && @annotation(secured)")
    public void checkAccess(Secured secured) {

        String[] requiredRoles = secured.value();
        log.info("Требуемые роли для доступа: {}", Arrays.toString(requiredRoles));

        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(userDetails instanceof User currentUser)) {
                log.error("Неправильный тип объекта UserDetails. Ожидался объект типа User, но получен: {}", userDetails.getClass().getName());
                throw new SecurityException("User details type mismatch");
            }


            if (!hasPermission(currentUser, requiredRoles)) {
                log.warn("Доступ запрещен для пользователя: {} с ролью: {}", currentUser.getUsername(), currentUser.getRole());
                throw new SecurityException("Access denied");
            }
        } catch (Exception e) {
            log.error("Ошибка проверки доступа: {}", e.getMessage());
            throw e;
        }
    }

    private boolean hasPermission(User currentUser, String[] requiredRoles) {
        return Arrays.stream(requiredRoles)
                .anyMatch(role -> currentUser.getRole().name().equals(role));
    }
}
