package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO для категории")
public record CategoryDTO(
        @Schema(description = "ID категории", example = "1")
        Long categoryID,

        @NotNull(message = "ID пользователя не может быть пустым")
        @Schema(description = "ID пользователя, связанного с категорией", example = "1", required = true)
        Long userID,

        @NotBlank(message = "Название категории не может быть пустым")
        @Size(min = 1, max = 100, message = "Название категории должно содержать от 1 до 100 символов")
        @Schema(description = "Название категории", example = "Продукты", required = true)
        String name,

        @Size(max = 20, message = "Цвет категории не должен превышать 20 символов")
        @Pattern(regexp = "^#[A-Fa-f0-9]{6}$", message = "Цвет категории должен быть в формате HEX (например, #FFFFFF)")
        @Schema(description = "Цвет категории в формате HEX", example = "#FF5733")
        String color
) {
}
