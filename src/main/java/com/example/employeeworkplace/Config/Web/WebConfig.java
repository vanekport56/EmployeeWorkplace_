package com.example.employeeworkplace.Config.Web;

import com.example.employeeworkplace.Converters.CustomJsonHttpMessageConverter;
import io.micrometer.common.lang.NonNullApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация Web MVC.
 * <p>
 * Этот класс настраивает ресурсы и CORS для приложения.
 * </p>
 */
@Slf4j
@NonNullApi
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.debug("Добавление контроллеров представлений");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("Настройка обработчиков ресурсов");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /**
     * Создает и настраивает бин {@link WebMvcConfigurer} для CORS.
     * <p>
     * Этот метод конфигурирует CORS для всех путей и методов.
     * </p>
     *
     * @return настроенный экземпляр {@link WebMvcConfigurer}
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        log.debug("Создание конфигуратора CORS");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
                log.debug("Настройка CORS для всех путей");
            }
        };
    }
    @Bean
    public CustomJsonHttpMessageConverter customJsonHttpMessageConverter() {
        return new CustomJsonHttpMessageConverter();
    }

    @Override
    public void extendMessageConverters(java.util.List<HttpMessageConverter<?>> converters) {
        converters.add(customJsonHttpMessageConverter());
    }
}
