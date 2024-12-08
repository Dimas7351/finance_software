package bank.develop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Config {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF-защиту, т.к. используем JWT
                .authorizeHttpRequests(auth -> auth
                        // JWT токен: разрешаем доступ к маршрутам авторизации без аутентификации
                        // JWT токен: для всех остальных маршрутов требуется аутентификация
                        .requestMatchers(
                                "/swagger-ui/**",  // Swagger UI статические файлы
                                "/v3/api-docs/**", // API-документация
                                "/auth/**",
                                "/test/**",
                                "/generate/**",
                                "/getTransactions/**"// Ваши публичные эндпоинты авторизации
                        ).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
