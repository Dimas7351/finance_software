package rpo.finance.software.controllers;

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
    public ResponseEntity<List<Transaction>> getTransactionsById(@PathVariable Long userId) {
        return bankAdapter.getTransactions(userId);
    }

    @PostMapping
    public ResponseEntity<List<Transaction>> getTransactionsByIdWithDate(@RequestBody DateTransactionDTO dateTransactionDto) {
        return bankAdapter.getTransactionsWithDate(dateTransactionDto);
    }
}
