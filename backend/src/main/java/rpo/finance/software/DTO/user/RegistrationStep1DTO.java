package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO для регистрации пользователей")
public record RegistrationStep1DTO(
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
        String password

) {
}
