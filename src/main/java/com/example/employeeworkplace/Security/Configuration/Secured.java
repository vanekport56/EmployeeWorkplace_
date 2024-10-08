package com.example.employeeworkplace.Security.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания, что доступ к методу должен быть ограничен на основе ролей.
 * Используется для аспектного контроля безопасности.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    String[] value();
}
