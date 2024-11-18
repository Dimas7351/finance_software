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
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryID")
    private Long categoryID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private Long userID;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "color", length = 20)
    private String color;

}

