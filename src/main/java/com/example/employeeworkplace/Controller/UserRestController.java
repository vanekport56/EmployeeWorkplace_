package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Security.CustomUserDetails;
import com.example.employeeworkplace.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получает данные пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с UserDTO или ошибка, если пользователь не найден
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        logger.info("Запрос на получение пользователя с ID: {}", id);

        try {
            User user = userService.findById(id);

            if (user == null) {
                logger.warn("Пользователь с ID {} не найден.", id);
                return ResponseEntity.notFound().build();
            }

            logger.info("Найден пользователь с ID {}: полное имя - {}", id, user.getFullName());

            UserDTO userDTO = new UserDTO();
            userDTO.setFullName(user.getFullName());

            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            logger.error("Ошибка при получении пользователя с ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить данные пользователя");
        }
    }

    /**
     * Получает ID текущего авторизованного пользователя.
     *
     * @return ID текущего пользователя
     */
    @GetMapping("/getId")
    public ResponseEntity<Long> getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                Long userId = ((CustomUserDetails) userDetails).getId();
                return ResponseEntity.ok(userId);
            } else {
                logger.warn("Не удалось получить ID текущего пользователя: аутентификация не найдена или principal не является UserDetails");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении ID текущего пользователя: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить ID текущего пользователя");
        }
    }
}
