package bank.develop.controllers;

import bank.develop.entities.Transaction;
import bank.develop.repositories.TransactionRepository;
import bank.develop.repositories.UserRepository;
import bank.develop.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("dimaaaaaaaaaaaa");
    }

    @GetMapping("/generate/{userId}")
    public ResponseEntity<String> generateTransactions(@PathVariable String userId) {

        transactionService.generateTransactions(userId);

        return ResponseEntity.ok("sdsd");
    }

    @GetMapping("/getTransactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsById(@PathVariable String userId) {

        return ResponseEntity.ok(transactionService.getAllTransactions(userId));
    }
}
