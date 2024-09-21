package com.example.employeeworkplace.Exceptions.ExceptionHandlers;

import com.example.employeeworkplace.Exceptions.CustomExceptions.EmailAlreadyExistsException;
import com.example.employeeworkplace.Exceptions.CustomExceptions.UsernameAlreadyExistsException;
import freemarker.core.NonBooleanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик глобальных исключений, перехватывающий различные ошибки и
 * предоставляющий соответствующие сообщения об ошибках для отображения пользователю.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Обрабатывает исключения, связанные с ошибками в шаблоне FreeMarker.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(NonBooleanException.class)
    public String handleNonBooleanException(NonBooleanException ex, Model model) {
        log.error("Ошибка в шаблоне: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Произошла ошибка при работе с шаблоном: " + ex.getMessage());
        return "error";
    }

    /**
     * Обрабатывает исключения, связанные с занятым именем пользователя.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public String handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, Model model) {
        log.warn("Имя пользователя уже занято: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Имя пользователя уже занято: " + ex.getMessage());
        return "error";
    }

    /**
     * Обрабатывает исключения, связанные с использованием уже существующего адреса электронной почты.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, Model model) {
        log.warn("Электронная почта уже используется: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Электронная почта уже используется: " + ex.getMessage());
        return "error";
    }

    /**
     * Обрабатывает все остальные исключения.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        log.error("Неизвестная ошибка: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "Внутренняя ошибка сервера: " + ex.getMessage());
        return "error";
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Ошибка целостности данных: " );
        response.put("message", ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
