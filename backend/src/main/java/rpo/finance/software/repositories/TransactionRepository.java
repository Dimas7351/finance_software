package rpo.finance.software.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rpo.finance.software.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
