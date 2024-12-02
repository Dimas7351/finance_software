package rpo.finance.software.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpo.finance.software.DTO.user.TransactionDTO;
import rpo.finance.software.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Transaction API", description = "Операции, связанные с транзакциями")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Получение всех транзакций")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список транзакций успешно получен")
    })
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение транзакции по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транзакция найдена"),
            @ApiResponse(responseCode = "404", description = "Транзакция не найдена")
    })
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создание новой транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Транзакция успешно создана")
    })
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO savedTransaction = transactionService.createTransaction(transactionDTO);
        return ResponseEntity.status(201).body(savedTransaction);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Транзакция успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Транзакция не найдена")
    })
    public ResponseEntity<TransactionDTO> updateTransaction(
            @PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(id, transactionDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление транзакции")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Транзакция успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Транзакция не найдена")
    })
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        if (transactionService.deleteTransaction(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
