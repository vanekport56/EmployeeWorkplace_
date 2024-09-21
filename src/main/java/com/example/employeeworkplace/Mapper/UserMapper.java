package com.example.employeeworkplace.Mapper;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Models.Secondary.User;
import org.mapstruct.Mapper;

/**
 * Mapper для преобразования между {@link User} и {@link UserDTO}.
 *
 * <p>Этот интерфейс используется для преобразования данных между сущностями и DTO.</p>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Преобразует объект {@link User} в {@link UserDTO}.
     *
     * @param user Объект {@link User}
     * @return Преобразованный объект {@link UserDTO}
     */
    UserDTO userToUserDTO(User user);

}
