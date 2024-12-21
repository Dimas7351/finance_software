package rpo.finance.software.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rpo.finance.software.DTO.DateTransactionDTO;
import rpo.finance.software.adapter.BankAdapter;
import rpo.finance.software.entities.Transaction;
import rpo.finance.software.jwt.JwtTokenUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/getTransactions") // Базовый путь для всех маршрутов
public class BankController {

    private final BankAdapter bankAdapter;
    private final JwtTokenUtil jwtTokenUtil; // Для работы с JWT

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionsByToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Извлекаем токен из заголовка
        String token = authorizationHeader.replace("Bearer ", "");

        // Извлекаем userId из токена
        Long userId = jwtTokenUtil.extractUserId(token);

        // Передаем userId в адаптер
        return bankAdapter.getTransactions(userId);
    }

    @PostMapping
    public ResponseEntity<List<Transaction>> getTransactionsWithDate(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DateTransactionDTO dateTransactionDto
    ) {
        // Извлекаем токен из заголовка
        String token = authorizationHeader.replace("Bearer ", "");

        // Извлекаем userId из токена
        Long userId = jwtTokenUtil.extractUserId(token);

        // Привязываем userId к запросу
        dateTransactionDto = new DateTransactionDTO(
                userId,
                dateTransactionDto.monthAmount()
        );

        // Передаем обновленный DTO в адаптер
        return bankAdapter.getTransactionsWithDate(dateTransactionDto);
    }
}
