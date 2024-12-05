package rpo.finance.software.entities;
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
    @JoinColumn(name = "user_id" ,nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @Column(name = "amount", nullable = false, precision = 12)
    private Double amount;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}