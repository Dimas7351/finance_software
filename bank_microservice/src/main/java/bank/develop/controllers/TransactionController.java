package bank.develop.controllers;

import bank.develop.entities.Transaction;
import bank.develop.entities.dto.DateTransactionDTO;
import bank.develop.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/generate/{userId}")
    public ResponseEntity<String> generateTransactions(@PathVariable String userId) {

        transactionService.generateTransactions(userId);

        return ResponseEntity.ok("sdsd");
    }

    @GetMapping("/getTransactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsById(@PathVariable String userId) {
        return ResponseEntity.ok(transactionService.getAllTransactions(userId));
    }

    @PostMapping("/getTransactions")
    public ResponseEntity<List<Transaction>> getTransactionsByIdWithDate(@RequestBody DateTransactionDTO dateTransactionDto) {
        return ResponseEntity.ok(transactionService.getAllTransactionsWithDate(dateTransactionDto));
    }
}
