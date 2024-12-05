package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO для регистрации пользователей")
public record RegistrationStep2DTO(
        @NotBlank(message = "Preferred currency cannot be blank")
        @Pattern(regexp = "RUB|USD|EUR|GBP", message = "Invalid currency. Allowed values: RUB, USD, EUR, GBP.")
        @Schema(description = "Preferred currency", example = "USD", required = true)
        String currency,

        @NotBlank(message = "Upload type cannot be blank")
        @Pattern(regexp = "fromBank|manual", message = "Invalid upload type. Allowed values: fromBank, manual.")
        @Schema(description = "Upload type", example = "manual", required = true)
        String uploadType
) {
}
