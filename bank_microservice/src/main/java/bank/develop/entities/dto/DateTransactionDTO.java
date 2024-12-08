package bank.develop.entities.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "DTO для транзакции")
public record DateTransactionDTO(

        @NotNull(message = "ID пользователя не может быть пустым")
        @Schema(description = "ID пользователя, связанного с транзакцией", example = "1", required = true)
        Long userId,

        @NotNull(message = "Количество месяцев не может быть пустым")
        @Schema(description = "Количество месяцев для выгрузки транзакций", example = "3", required = true)
        Integer monthAmount
) {
}
