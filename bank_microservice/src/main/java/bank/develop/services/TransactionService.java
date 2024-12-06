package bank.develop.services;

import bank.develop.entities.Transaction;
import bank.develop.entities.User;
import bank.develop.enums.CategoryEnum;
import bank.develop.enums.PurchaseTypeEnum;
import bank.develop.repositories.TransactionRepository;
import bank.develop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Async
    public void generateTransactions(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i<50; i++){
            Transaction transaction = Transaction.builder()
                    .userID(user)
                    .categoryId(CategoryEnum.randomCategory())
                    .type(PurchaseTypeEnum.randomPurchaseType().toString())
                    .build();
            transactions.add(transaction);
        }
        transactionRepository.saveAll(transactions);
    }

    public List<Transaction> getAllTransactions(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
        return transactionRepository.findAllByUserID(user);
    }
}
