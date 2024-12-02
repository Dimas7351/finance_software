package bank.develop.entities;

import bank.develop.enums.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id" ,nullable = false)
    private User userID;

    @JoinColumn(name = "category_id")
    private CategoryEnum categoryId;

    @Column(name = "amount", precision = 12)
    private Double amount;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "date")
    private LocalDateTime date;
}