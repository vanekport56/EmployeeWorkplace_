package com.example.employeeworkplace.Security;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Конфигурация безопасности приложения.
 * Настраивает фильтры, аутентификацию и авторизацию пользователей.
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * Конфигурирует цепочку фильтров безопасности.
     *
     * @param http объект HttpSecurity для настройки безопасности
     * @return сконфигурированная цепочка фильтров безопасности
     * @throws Exception если возникнут проблемы при настройке
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Начало настройки цепочки фильтров безопасности");

        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/admin/**").hasRole("admin")
                        .requestMatchers("/profile/**", "/swagger-ui.html", "/v3/api-docs/**").authenticated()
                        .requestMatchers("/register", "/login", "/public/**", "/css/**", "/js/**", "/images/**", "/users/**").permitAll()
                        .requestMatchers("/users/**").hasRole("user")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profile/", true)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .disable()
                );

        http.addFilterBefore(new ProfileCompletionFilter(), UsernamePasswordAuthenticationFilter.class);
        log.info("Конфигурация безопасности завершена");

        return http.build();
    }

    /**
     * Создает бин BCryptPasswordEncoder для шифрования паролей.
     *
     * @return созданный BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("Создание бина BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает бин фильтра для установки кодировки UTF-8.
     *
     * @return созданный FilterRegistrationBean для CharacterEncodingFilter
     */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> customCharacterEncodingFilter() {
        log.debug("Создание бина CharacterEncodingFilter с кодировкой UTF-8");
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(filter);
        filterBean.addUrlPatterns("/*");

        return filterBean;
    }

    /**
     * Создает бин DaoAuthenticationProvider для аутентификации пользователей.
     *
     * @param customUserDetailsService сервис для получения данных пользователя
     * @return сконфигурированный DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        log.debug("Создание бина DaoAuthenticationProvider");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // Ваш UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Ваш BCryptPasswordEncoder
        return authProvider;
    }
}
