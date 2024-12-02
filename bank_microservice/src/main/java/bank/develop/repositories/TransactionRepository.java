package bank.develop.repositories;

import bank.develop.entities.Transaction;
import bank.develop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllByUserID(User userID);
}
