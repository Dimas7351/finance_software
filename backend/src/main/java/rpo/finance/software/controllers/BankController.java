package rpo.finance.software.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpo.finance.software.DTO.DateTransactionDTO;
import rpo.finance.software.adapter.BankAdapter;
import rpo.finance.software.entities.Transaction;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/getTransactions")
public class BankController {

    private final BankAdapter bankAdapter;

    @GetMapping("/{userId}")
    @Operation(summary = "Получение всех транзакций по userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно вернулись")})
    public ResponseEntity<List<Transaction>> getTransactionsById(@PathVariable Long userId) {
        return bankAdapter.getTransactions(userId);
    }

    @PostMapping
    @Operation(summary = "Получение всех транзакций по userId с указанием количества месяцев")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно вернулись")})
    public ResponseEntity<List<Transaction>> getTransactionsByIdWithDate(@RequestBody DateTransactionDTO dateTransactionDto) {
        return bankAdapter.getTransactionsWithDate(dateTransactionDto);
    }
}
