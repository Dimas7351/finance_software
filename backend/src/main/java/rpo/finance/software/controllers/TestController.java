package rpo.finance.software.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rpo.finance.software.adapter.BankAdapter;

@RestController
@RequestMapping("/test")
public class TestController {

    private final BankAdapter bankAdapter;

    public TestController(BankAdapter bankAdapter) {
        this.bankAdapter = bankAdapter;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return bankAdapter.bankTest();
    }

}
