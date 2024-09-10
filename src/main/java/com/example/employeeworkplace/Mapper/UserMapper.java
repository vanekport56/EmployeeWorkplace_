package com.example.employeeworkplace.Mapper;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Models.Secondary.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


//Еще не реализовано
/**
 * Mapper для преобразования между {@link User} и {@link UserDTO}.
 *
 * <p>Этот интерфейс используется для преобразования данных между сущностями и DTO.</p>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Экземпляр {@link UserMapper}.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Преобразует объект {@link User} в {@link UserDTO}.
     *
     * @param user Объект {@link User}
     * @return Преобразованный объект {@link UserDTO}
     */
    UserDTO userToUserDTO(User user);

    /**
     * Преобразует объект {@link UserDTO} в {@link User}.
     *
     * @param userDTO Объект {@link UserDTO}
     * @return Преобразованный объект {@link User}
     */
    User userDTOToUser(UserDTO userDTO);
}
