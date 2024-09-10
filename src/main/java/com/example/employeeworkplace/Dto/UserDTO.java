package com.example.employeeworkplace.Dto;

import lombok.Data;

/**
 * Класс нужен для представления полного имени о пользователе из другой базы, получаемого по ключу.
 *
 * <p>Используется для передачи полного имени о пользователе из другой базы, получаемого по ключу.</p>
 *
 */
@Data
public class UserDTO {

    private String fullName;
}
