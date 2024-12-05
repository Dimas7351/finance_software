package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationStep3DTO(
        @NotBlank(message = "Bank name cannot be blank")
        @Pattern(regexp = "TINKOFF|SBER|ALFA|VTB", message = "Invalid bank name. Allowed values: TINKOFF, SBER, ALFA, VTB.")
        @Schema(description = "Bank name", example = "TINKOFF", required = true)
        String bankName,

        @NotBlank(message = "Номер телефона не может быть пустым")
        @Size(max = 18, message = "Номер телефона не должен превышать 18 символов")
        @Pattern(regexp = "(^\\+7|7|8)[0-9]{10}$|^\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
                message = "Неверный формат номера телефона. Используйте формат +7 (XXX) XXX-XX-XX или 7XXXXXXXXXX.")
        @Schema(name = "phoneNumber", example = "+7 (123) 456-78-90",
        description = "Номер телефона пользователя. Должен быть в формате: +7 (XXX) XXX-XX-XX.")
        String phoneNumber
) {
}
