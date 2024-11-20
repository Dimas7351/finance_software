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
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionID")
    private Long transactionID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;  // Change to reference the User entity, not Long

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;  // Change to reference the Category entity, not Long

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}


