package bank.develop.services;

import bank.develop.entities.Transaction;
import bank.develop.entities.User;
import bank.develop.entities.dto.DateTransactionDTO;
import bank.develop.enums.CategoryEnum;
import bank.develop.enums.PurchaseTypeEnum;
import bank.develop.repositories.TransactionRepository;
import bank.develop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final Random random = new Random();
//    private final DateConverter dateConverter;


    public List<Transaction> getAllTransactions(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
        return transactionRepository.findAllByUserID(user);
    }

    public List<Transaction> getAllTransactionsWithDate(DateTransactionDTO dateTransactionDTO) {
        LocalDateTime dateTo = LocalDateTime.now();
        LocalDateTime dateFrom = dateTo.minusMonths(dateTransactionDTO.monthAmount());
        Long userID = dateTransactionDTO.userID();
        return transactionRepository.findAllByUserIDWithDate(userID, dateFrom, dateTo);
    }

    @Async
    public void generateTransactions(String userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
        List<Transaction> transactions = new ArrayList<>();
        int month = LocalDateTime.now().getMonthValue() - 1;

        for (int m = month; m > 0; m--) {
            if (m == 0) {
                m = 12;
            }

            // --sending money for home--
            for (int i = 0; i < 12; i++) {
                transactions.add(Transaction.builder()
                        .userID(user)
                        .categoryId(CategoryEnum.TRANSFER)
                        .amount(Double.valueOf(80000))
                        .type(String.valueOf(PurchaseTypeEnum.OUTCOME))
                        .date(generateRandomDateTime(m))
                        .build());
            }
            // --restaurants--
            for (int i = 0; i < 15; i++) {
                transactions.add(Transaction.builder()
                        .userID(user)
                        .categoryId(CategoryEnum.CAFE)
                        .amount(generateAmountOutcome())
                        .type(String.valueOf(PurchaseTypeEnum.OUTCOME))
                        .date(generateRandomDateTime(m))
                        .build());
            }

            //  --products--
            for (int i = 0; i < 23; i++) {
                transactions.add(Transaction.builder()
                        .userID(user)
                        .categoryId(CategoryEnum.FOOD)
                        .amount(generateAmountOutcome())
                        .type(String.valueOf(PurchaseTypeEnum.OUTCOME))
                        .date(generateRandomDateTime(m))
                        .build());
            }

            // --health--
            for (int i = 0; i < 4; i++) {
                transactions.add(Transaction.builder()
                        .userID(user)
                        .categoryId(CategoryEnum.HEALTH)
                        .amount(generateAmountOutcome())
                        .type(String.valueOf(PurchaseTypeEnum.OUTCOME))
                        .date(generateRandomDateTime(m))
                        .build());
            }

            //  --education--
            for (int i = 0; i < 1; i++) {
                transactions.add(Transaction.builder()
                        .userID(user)
                        .categoryId(CategoryEnum.EDUCATION)
                        .amount(generateAmountOutcome())
                        .type(String.valueOf(PurchaseTypeEnum.OUTCOME))
                        .date(generateRandomDateTime(m))
                        .build());
            }

            //  --sports--
            for (int i = 0; i < 2; i++) {
                transactions.add(Transaction.builder()
                        .userID(user)
                        .categoryId(CategoryEnum.SPORTS)
                        .amount(2999.99)
                        .type(String.valueOf(PurchaseTypeEnum.OUTCOME))
                        .date(generateRandomDateTime(m))
                        .build());
            }

            // --gifts--
            for (int i = 0; i < 1; i++) {
                transactions.add(Transaction.builder()
                        .userID(user)
                        .categoryId(CategoryEnum.GIFTS)
                        .amount(generateAmountOutcome())
                        .type(String.valueOf(PurchaseTypeEnum.OUTCOME))
                        .date(generateRandomDateTime(m))
                        .build());
            }
        }

        //  --INCOME--
        for (int i = 0; i < 16; i++) {
            if (month == 0) {
                month = 12;
            }
            transactions.add(Transaction.builder()
                    .userID(user)
                    .categoryId(null)
                    .amount(generateAmountIncome())
                    .type(String.valueOf(PurchaseTypeEnum.INCOME))
                    .date(generateRandomDateTime(month--))
                    .build());
        }
        transactionRepository.saveAll(transactions);
    }


    private LocalDateTime generateRandomDateTime(int month) {
        int year = LocalDateTime.now().getYear();

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);

        LocalDateTime end = start.plusMonths(1).minusSeconds(1);

        long startSeconds = start.toEpochSecond(java.time.ZoneOffset.UTC);
        long endSeconds = end.toEpochSecond(java.time.ZoneOffset.UTC);
        long randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);

        return LocalDateTime.ofEpochSecond(randomSeconds, 0, java.time.ZoneOffset.UTC);
    }

    private Double generateAmountOutcome() {
        double amount = 500 + (30000 - 500) * random.nextDouble();
        return Math.round(amount * 100.0) / 100.0;
    }

    private Double generateAmountIncome() {
        double amount = 10000 + (100000 - 500) * random.nextDouble();
        return Math.round(amount * 100.0) / 100.0;
    }
}
