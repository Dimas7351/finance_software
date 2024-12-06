package bank.develop.repositories;

import bank.develop.entities.Transaction;
import bank.develop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllByUserID(User userID);

    @NativeQuery("select * from transaction t where t.user_id = :userId and date between :dateFrom and :dateTo")
    public List<Transaction> findAllByUserIDWithDate(
            @Param("userId") Long userID,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );
}
