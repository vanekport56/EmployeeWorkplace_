package com.example.employeeworkplace.Exceptions;

/**
 * Исключение, которое выбрасывается, когда имя пользователя уже существует в системе.
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    /**
     * Конструктор исключения с сообщением.
     *
     * @param message Сообщение об ошибке
     */
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
