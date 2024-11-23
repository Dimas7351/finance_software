package rpo.finance.software.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rpo.finance.software.jwt.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    // JWT токен: внедряем кастомный фильтр для проверки JWT-токенов
    private final JwtAuthFilter jwtAuthFilter;

    // JWT токен: конструктор принимает JwtAuthenticationFilter
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Для хэширования паролей
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF-защиту, т.к. используем JWT
                .authorizeHttpRequests(auth -> auth
                        // JWT токен: разрешаем доступ к маршрутам авторизации без аутентификации
                        .requestMatchers("/auth/**").permitAll()
                        // JWT токен: для всех остальных маршрутов требуется аутентификация
                        .anyRequest().authenticated()
                )
                // JWT токен: добавляем кастомный фильтр перед стандартным фильтром аутентификации
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // JWT токен: предоставляем AuthenticationManager, используемый в процессе аутентификации
        return config.getAuthenticationManager();
    }
}
