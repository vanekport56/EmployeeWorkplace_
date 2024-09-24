package com.example.employeeworkplace.Controller.Mvc.UserAndOther;

import com.example.employeeworkplace.Exceptions.CustomExceptions.EmailAlreadyExistsException;
import com.example.employeeworkplace.Exceptions.CustomExceptions.UsernameAlreadyExistsException;
import com.example.employeeworkplace.Models.ConstantsAndEnums.Gender;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.UserServices.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Контроллер для управления пользователями.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Отображает профиль текущего пользователя.
     *
     * @param model объект модели для передачи данных в представление
     * @param authentication объект аутентификации текущего пользователя
     * @return имя шаблона для страницы профиля
     */
    @GetMapping("/profile/")
    public String profile(Model model, Authentication authentication) {
        User currentUser = userService.getUserByUsername(authentication.getName());



        currentUser.setFullName(Optional.ofNullable(currentUser.getFullName()).orElse(""));
        currentUser.setPosition(Optional.ofNullable(currentUser.getPosition()).orElse(""));
        currentUser.setBirthDate(currentUser.getBirthDate());
        currentUser.setEmail(Optional.ofNullable(currentUser.getEmail()).orElse(""));
        currentUser.setPhoneNumber(Optional.ofNullable(currentUser.getPhoneNumber()).orElse(""));
        currentUser.setGender(Optional.ofNullable(currentUser.getGender()).orElse(Gender.MALE));


        List<Gender> genders = List.of(Gender.MALE, Gender.FEMALE);
        model.addAttribute("user", currentUser);
        model.addAttribute("genders", genders);
        model.addAttribute("userRoles", currentUser.getRole());

        log.debug("Отображение профиля для пользователя: {}", authentication.getName());
        return "ProfileTemplates/Profile";
    }


    /**
     * Отображает страницу регистрации нового пользователя.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона для страницы регистрации
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        log.debug("Отображение страницы регистрации");
        return "AuthTemplates/Register";
    }

    /**
     * Обрабатывает отправку формы регистрации нового пользователя.
     *
     * @param user объект пользователя с данными из формы
     * @param result результат валидации формы
     * @param username имя пользователя
     * @param password пароль
     * @param confirmPassword подтверждение пароля
     * @param email email (может быть null)
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона для страницы регистрации или перенаправление на страницу входа
     */
    @PostMapping("/register")
    public String registerSubmit(@Validated User user, BindingResult result, @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam(value = "email", required = false) String email, Model model) {
        if (result.hasErrors()) {
            log.debug("Ошибка валидации при регистрации пользователя: {}", result.getAllErrors());
            return "AuthTemplates/Register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают");
            log.warn("Пароли не совпадают при регистрации пользователя: {}", username);
            return "AuthTemplates/Register";
        }

        try {
            userService.saveNewUser(user);
            user.setPassword(passwordEncoder.encode(password));
            log.info("Новый пользователь успешно зарегистрирован: {}", username);
        } catch (UsernameAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            log.error("Ошибка регистрации: имя пользователя уже существует: {}", username, e);
            return "AuthTemplates/Register";
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            log.error("Ошибка регистрации: email уже существует: {}", email, e);
            return "AuthTemplates/Register";
        }

        return "redirect:/login";
    }

    /**
     * Обрабатывает обновление профиля текущего пользователя.
     *
     * @param user объект пользователя с обновленными данными
     * @param result результат валидации формы
     * @param model объект модели для передачи данных в представление
     * @param authentication объект аутентификации текущего пользователя
     * @return имя шаблона для страницы профиля
     */
    @PostMapping("/profile/update")
    public String updateProfile(@Validated User user, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            log.debug("Ошибка валидации при обновлении профиля пользователя: {}", result.getAllErrors());
            return "ProfileTemplates/Profile";
        }

        User currentUser = userService.getUserByUsername(authentication.getName());


        currentUser.setFullName(user.getFullName());
        currentUser.setPosition(user.getPosition());
        currentUser.setBirthDate(user.getBirthDate());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setGender(user.getGender());

        userService.editingUser(currentUser);

        List<Gender> genders = List.of(Gender.MALE, Gender.FEMALE);
        model.addAttribute("user", currentUser);
        model.addAttribute("genders", genders);
        model.addAttribute("userRoles", currentUser.getRole());

        log.info("Профиль пользователя успешно обновлён: {}", authentication.getName());
        return "ProfileTemplates/Profile";
    }

    /**
     * Отображает страницу входа.
     *
     * @param model объект модели для передачи данных в представление
     * @param request объект запроса
     * @return имя шаблона для страницы входа
     */
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        log.debug("Отображение страницы входа");
        return "AuthTemplates/Login";
    }

    /**
     * Отображает страницу смены пароля.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона для страницы смены пароля
     */
    @GetMapping("/changePassword")
    public String passwordChange(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("success", "");
        log.debug("Отображение страницы смены пароля");
        return "ProfileTemplates/PasswordСhange";
    }

    /**
     * Обрабатывает смену пароля текущего пользователя.
     *
     * @param requestData данные запроса в формате ключ-значение
     * @param authentication объект аутентификации текущего пользователя
     * @return ответ с результатом операции смены пароля
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody Map<String, String> requestData,
            Authentication authentication) {

        String currentPassword = requestData.get("currentPassword");
        String newPassword = requestData.get("newPassword");
        String confirmPassword = requestData.get("confirmPassword");

        Map<String, String> response = new HashMap<>();

        if (!newPassword.equals(confirmPassword)) {
            response.put("error", "Пароли не совпадают!");
            log.warn("Пароли не совпадают при смене пароля: {}", authentication.getName());
            return ResponseEntity.badRequest().body(response);
        }

        UserDetails currentUser = (UserDetails) authentication.getPrincipal();

        boolean isPasswordChanged = userService.changePassword(currentUser.getUsername(), currentPassword, newPassword);

        if (!isPasswordChanged) {
            response.put("error", "Не удалось изменить пароль. Проверьте правильность текущего пароля.");
            log.error("Не удалось изменить пароль для пользователя: {}", currentUser.getUsername());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", "Пароль успешно изменен.");
        log.info("Пароль успешно изменен для пользователя: {}", currentUser.getUsername());
        return ResponseEntity.ok(response);
    }
}
