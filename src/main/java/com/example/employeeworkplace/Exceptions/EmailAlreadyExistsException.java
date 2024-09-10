package com.example.employeeworkplace.Exceptions;

/**
 * Исключение, которое выбрасывается, когда электронная почта уже существует в системе.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
