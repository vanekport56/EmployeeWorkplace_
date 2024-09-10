package com.example.employeeworkplace.Exceptions;

/**
 * Исключение, которое выбрасывается, когда пароль введен неверно.
 */
public class IncorrectPasswordException extends RuntimeException {

    /**
     * Конструктор исключения с сообщением.
     *
     * @param message Сообщение об ошибке
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
