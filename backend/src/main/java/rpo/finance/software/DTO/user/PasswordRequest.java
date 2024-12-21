package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRequest(
        @NotBlank
        @Size(min = 7, max = 255, message = "Пароль должна быть от 7 символов")
        @Schema(name = "password", example = "pass123123", required = true,
                description = "Пароль пользователя. Должен быть от 7 до 255 символов.")
        String oldPassword,

        @NotBlank
        @Size(min = 7, max = 255, message = "Пароль должна быть от 7 символов")
        @Schema(name = "password", example = "pass123123", required = true,
                description = "Пароль пользователя. Должен быть от 7 до 255 символов.")
        String newPassword
) {
}