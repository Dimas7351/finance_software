package rpo.finance.software.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bankIntegration")
public class BankIntegration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "integration_id")
    private Long integrationID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userID;

    @Column(name = "bankName", nullable = false, length = 100)
    private String bank_name;

    @Column(name = "keyAPI", nullable = false, length = 255)
    private String keyAPI;

    @Column(name = "status", nullable = false)
    private Boolean status = true;
}

