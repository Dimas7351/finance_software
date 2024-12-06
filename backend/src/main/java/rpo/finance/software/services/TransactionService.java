package rpo.finance.software.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rpo.finance.software.DTO.user.TransactionDTO;
import rpo.finance.software.entities.Category;
import rpo.finance.software.entities.Transaction;
import rpo.finance.software.entities.User;
import rpo.finance.software.repositories.CategoryRepository;
import rpo.finance.software.repositories.TransactionRepository;
import rpo.finance.software.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id).map(this::mapToDTO);
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setDate(dto.date());
        transaction.setUserID(getUserById(dto.userID()));

        if (dto.categoryID() != null) {
            transaction.setCategoryID(getCategoryById(dto.categoryID()));
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToDTO(savedTransaction);
    }

    public Optional<TransactionDTO> updateTransaction(Long id, TransactionDTO dto) {
        return transactionRepository.findById(id).map(existingTransaction -> {
            existingTransaction.setAmount(dto.amount());
            existingTransaction.setType(dto.type());
            existingTransaction.setDate(dto.date());
            existingTransaction.setUserID(getUserById(dto.userID()));

            if (dto.categoryID() != null) {
                existingTransaction.setCategoryID(getCategoryById(dto.categoryID()));
            }

            Transaction updatedTransaction = transactionRepository.save(existingTransaction);
            return mapToDTO(updatedTransaction);
        });
    }

    public boolean deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /*
    mapToDTO — это метод для "перекладывания" данных из сложной сущности
    в упрощённый объект для передачи клиенту через API.
    */
    private TransactionDTO mapToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getTransactionID(),
                transaction.getUserID().getUserID(),
                transaction.getCategoryID() != null ? transaction.getCategoryID().getCategoryID() : null,
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate()
        );
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + userId + " не найден."));
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + categoryId + " не найдена."));
    }
}
