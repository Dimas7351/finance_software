package bank.develop.controllers;

import bank.develop.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/bank")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

//    @GetMapping("/generate/{userId}")
//    public ResponseEntity<String> generateTransactions(@PathVariable String userId) {
//
//        transactionService.generateTransactions(userId);
//
//        return ResponseEntity.ok("dimaaaaaaaaaaaa");
//    }

}
