package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Dto.UserDTO;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

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
        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setFullName(user.getFullName());

        return ResponseEntity.ok(userDTO);
    }
}
