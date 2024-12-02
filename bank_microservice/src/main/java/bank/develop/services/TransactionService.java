package bank.develop.services;

import bank.develop.entities.Transaction;
import bank.develop.entities.User;
import bank.develop.enums.CategoryEnum;
import bank.develop.enums.PurchaseTypeEnum;
import bank.develop.repositories.TransactionRepository;
import bank.develop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public void generateTransactions(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
        for (int i = 0; i<5; i++){
            Transaction transaction = Transaction.builder()
                    .userID(user)
                    .categoryId(CategoryEnum.randomCategory())
                    .type(PurchaseTypeEnum.randomPurchaseType().toString())
                    .build();
            transactionRepository.save(transaction);
        }
    }

    public List<Transaction> getAllTransactions(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
        return transactionRepository.findAllByUserID(user);
    }

}
