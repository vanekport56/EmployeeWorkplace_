package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Models.Secondary.Gender;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * Контроллер для завершения профиля пользователя.
 * <p>
 * Этот контроллер обрабатывает запросы на отображение и обновление профиля пользователя.
 * </p>
 */
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileCompletionController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileCompletionController.class);

    private final UserService userService;

    /**
     * Отображает форму для заполнения профиля пользователя, если данные неполные.
     * <p>
     * Этот метод проверяет, какие данные отсутствуют у пользователя, и отображает форму
     * для их заполнения.
     * </p>
     *
     * @param model объект модели для передачи данных в представление
     * @param principal объект аутентификации текущего пользователя
     * @return имя шаблона для страницы заполнения профиля
     */
    @GetMapping("/incomplete")
    public String showIncompleteProfileForm(Model model, Principal principal) {
        logger.debug("Получение данных пользователя для завершения профиля. Имя пользователя: {}", principal.getName());

        User user = userService.getUserByUsername(principal.getName());

        if (user == null) {
            logger.error("Пользователь не найден: {}", principal.getName());
            return "error";
        }


        user.setFullName(Optional.ofNullable(user.getFullName()).orElse(""));
        user.setGender(Optional.ofNullable(user.getGender()).orElse(Gender.MALE));
        user.setPosition(Optional.ofNullable(user.getPosition()).orElse(""));
        user.setBirthDate(Optional.ofNullable(user.getBirthDate()).orElse(Date.valueOf("2000-01-01")));
        user.setEmail(Optional.ofNullable(user.getEmail()).orElse(""));
        user.setPhoneNumber(Optional.ofNullable(user.getPhoneNumber()).orElse(""));
        user.setProfilePhoto(Optional.ofNullable(user.getProfilePhoto()).orElse(""));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedBirthDate = user.getBirthDate() != null ? formatter.format(user.getBirthDate()) : "";

        model.addAttribute("user", user);
        model.addAttribute("formattedBirthDate", formattedBirthDate);
        model.addAttribute("genders", Gender.values());

        logger.debug("Форма для неполного профиля отображена для пользователя: {}", principal.getName());
        return "ProfileIncomplete";
    }

    /**
     * Обновляет профиль пользователя на основе данных из формы.
     * <p>
     * Этот метод сохраняет обновленные данные профиля пользователя в базе данных.
     * </p>
     *
     * @param user объект пользователя с обновленными данными
     * @param principal объект аутентификации текущего пользователя
     * @return URL для перенаправления после успешного обновления
     */
    @PostMapping("/incomplete")
    public String updateProfile(@ModelAttribute User user, Principal principal) {
        logger.debug("Обновление профиля для пользователя: {}", principal.getName());

        User existingUser = userService.getUserByUsername(principal.getName());

        if (existingUser == null) {
            logger.error("Пользователь не найден для обновления: {}", principal.getName());
            return "error";
        }


        Optional.ofNullable(user.getFullName()).ifPresent(existingUser::setFullName);
        Optional.ofNullable(user.getPosition()).ifPresent(existingUser::setPosition);
        Optional.ofNullable(user.getBirthDate()).ifPresent(existingUser::setBirthDate);
        Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(user.getPhoneNumber()).ifPresent(existingUser::setPhoneNumber);
        Optional.ofNullable(user.getProfilePhoto()).ifPresent(existingUser::setProfilePhoto);
        Optional.ofNullable(user.getGender()).ifPresent(existingUser::setGender);

        userService.editingUser(existingUser);

        logger.info("Профиль пользователя успешно обновлён. Имя пользователя: {}", principal.getName());
        return "redirect:/";
    }
}
