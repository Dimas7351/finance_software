package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "DTO для регистрации пользователей")
public record UserRegistrationDTO (
        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 1, max = 100, message = "Имя должно содержать от 1 до 100 символов")
        @Schema(description = "Имя пользователя", example = "Карам", required = true)
        String name,

        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный email адрес")
        @Schema(description = "Email пользователя", example = "karam@hotmail.com", required = true)
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 7, max = 255, message = "Пароль должен быть не менее 7 символов")
        @Schema(description = "Пароль пользователя", example = "pass12345", required = true)
        String password,

        @NotNull(message = "Дата рождения не может быть пустой")
        @Past(message = "Дата рождения должна быть в прошлом")
        //@JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Дата рождения пользователя", example = "2002-04-11", required = true)
        LocalDate birthday,

        @NotBlank(message = "Номер телефона не может быть пустым")
        @Size(max = 18, message = "Номер телефона не должен превышать 18 символов")
        @Pattern(regexp = "(^\\+7|7|8)[0-9]{10}$|^\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
                message = "Неверный формат номера телефона. Используйте формат +7 (XXX) XXX-XX-XX или 7XXXXXXXXXX.")
        @Schema(name = "phoneNumber", example = "+7 (123) 456-78-90",
                description = "Номер телефона пользователя. Должен быть в формате: +7 (XXX) XXX-XX-XX.")
        String phoneNumber
) {
}
