package com.example.employeeworkplace.Models.Secondary;

import com.example.employeeworkplace.Models.ConstantsAndEnums.Gender;
import com.example.employeeworkplace.Models.ConstantsAndEnums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Класс представляет сущность пользователя в системе.
 * Реализует интерфейс {@link UserDetails} для работы с Spring Security.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя для входа в систему.
     * Уникально и не может быть пустым.
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * Пароль пользователя.
     * Не может быть пустым.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Полное имя пользователя.
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * Пол пользователя, представленный перечислением {@link Gender}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    /**
     * Должность, которую занимает пользователь.
     */
    @Column(name = "position")
    private String position;

    /**
     * Дата рождения пользователя.
     */
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * Электронная почта пользователя.
     * Не может быть пустой.
     */
    @Column(name = "email", nullable = false)
    @Email
    private String email;

    /**
     * Номер телефона пользователя.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Ссылка на фото профиля пользователя.
     */
    @Column(name = "profile_photo")
    private String profilePhoto;

    /**
     * Роль пользователя, представленная перечислением {@link Role}.
     * Не может быть пустой.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    /**
     * Возвращает роли пользователя в системе.
     *
     * @return список прав доступа (пока пустой)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Указывает, истек ли срок действия учетной записи пользователя.
     *
     * @return true, если учетная запись не истекла
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Указывает, заблокирована ли учетная запись пользователя.
     *
     * @return true, если учетная запись не заблокирована
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Указывает, истек ли срок действия учетных данных (пароля) пользователя.
     *
     * @return true, если учетные данные не истекли
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Указывает, включен ли пользователь в систему.
     *
     * @return true, если учетная запись активна
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
