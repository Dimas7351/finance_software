package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для входа пользователя")
public record UserSignInDTO(
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный email адрес")
        @Schema(description = "Email пользователя", example = "karam@hotmail.com", required = true)
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 7, max = 255, message = "неправильный пароль")
        @Schema(description = "Пароль пользователя", example = "pass12345", required = true)
        String password
) {
}
