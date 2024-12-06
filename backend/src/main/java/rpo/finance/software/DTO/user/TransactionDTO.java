package rpo.finance.software.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "DTO для транзакции")
public record TransactionDTO(
        @Schema(description = "ID транзакции", example = "1")
        Long transactionID,

        @NotNull(message = "ID пользователя не может быть пустым")
        @Schema(description = "ID пользователя, связанного с транзакцией", example = "1", required = true)
        Long userID,

        @Schema(description = "ID категории, связанной с транзакцией", example = "1")
        Long categoryID,

        @NotNull(message = "Сумма транзакции не может быть пустой")
        @Positive(message = "Сумма транзакции должна быть положительным числом")
        @Schema(description = "Сумма транзакции", example = "1500.75", required = true)
        Double amount,

        @NotBlank(message = "Тип транзакции не может быть пустым")
        @Pattern(regexp = "^(income|expense)$", message = "Тип транзакции должен быть INCOME или EXPENSE")
        @Schema(description = "Тип транзакции", example = "income", required = true)
        String type,

        @NotNull(message = "Дата транзакции не может быть пустой")
        @PastOrPresent(message = "Дата транзакции должна быть в прошлом или настоящем времени")
        @Schema(description = "Дата транзакции", example = "2023-12-01T15:30:00", required = true)
        LocalDateTime date
) {
}
