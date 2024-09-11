    package com.example.employeeworkplace.Security;

    import com.example.employeeworkplace.Models.Secondary.User;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.Collection;
    import java.util.List;

    /**
     * Реализация UserDetails для работы с Spring Security.
     */
    public class CustomUserDetails implements UserDetails {

        private final User user;
        /**
         * Конструктор для создания экземпляра {@link CustomUserDetails}.
         *
         * @param user экземпляр {@link User}, который будет использоваться для предоставления информации о пользователе.
         */

        public CustomUserDetails(User user) {
            this.user = user;
        }
        /**
         * Возвращает роли пользователя в системе.
         * <p>Создаёт {@link GrantedAuthority} с префиксом "ROLE_" для роли пользователя.</p>
         *
         * @return коллекция ролей пользователя.
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
        /**
         * Возвращает ID пользователя.
         *
         * @return ID пользователя.
         */
        public Long getId() {
            return user.getId();
        }

    }
