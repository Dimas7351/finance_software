package rpo.finance.software.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Schema(description = "пользователь Entity")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "userID", example = "1", required = true, description = "Уникальный идентификатор пользователя.")
    private Long userID;

    @NotBlank
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    @Schema(name = "name", example = "Карам", required = true, description = "Имя пользователя. Должно быть от 1 до 100 символов.")
    @Size(min = 1, max = 100, message = "Имя должно быть от 1 до 100 символов")
    private String name;

    @Column(name = "phone_number")
    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(max = 18, message = "Номер телефона не должен превышать 18 символов")
    @Pattern(regexp = "(^\\+7|7|8)[0-9]{10}$|^\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
            message = "Неверный формат номера телефона. Используйте формат +7 (XXX) XXX-XX-XX или 7XXXXXXXXXX.")
    @Schema(name = "phoneNumber", example = "+7 (123) 456-78-90",
            description = "Номер телефона пользователя. Должен быть в формате: +7 (XXX) XXX-XX-XX.")
    private String phoneNumber;

    @Past
    @Column(name = "date_of_birth")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(name = "birthday", example = "2002-11-04", description = "Дата рождения пользователя. Формат: yyyy-MM-dd.")
    private LocalDate birthday;

    @Email(message = "Введите действительный адрес электронной почты")
    @Column(name = "email", nullable = false, unique = true)
    /*@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)" +
            "*(\\.[A-Za-z]{2,})$",message = "Введите действительный адрес электронной почты")*/
    @Schema(name = "email", example = "2002@hotmail.com", required = true,
            description = "Электронная почта пользователя. Должна быть уникальной и в правильном формате.")
    private String email;

    @Column(name = "is_verified")
    @Schema(name = "isVerified", example = "false",
            description = "Электронная почта должна быть подтверждена с помощью верификации по электронной почте")
    private Boolean isVerified = false;

    @Column(name = "password", nullable = false, length = 255)
    @Size(min = 7, max = 255, message = "Пароль должна быть от 7 символов")
    @Schema(name = "password", example = "pass123123", required = true,
            description = "Пароль пользователя. Должен быть от 7 до 255 символов.")
    private String password;

    @Column(name = "currency", length = 10)
    @Schema(name = "currency", example = "USD", description = "Предпочтительная валюта пользователя (по умолчанию USD).")
    private String currency = "USD";

    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список категорий, связанных с пользователем.")
    private List<Category> categories;

    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список транзакций, совершенных пользователем.")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список банковских интеграций пользователя.")
    private List<BankIntegration> bankIntegrations;


}

