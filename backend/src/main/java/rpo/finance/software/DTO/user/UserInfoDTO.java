package rpo.finance.software.DTO.user;
import java.time.LocalDate;

public record UserInfoDTO(
        Long id,
        String name,
        String email,
        String phoneNumber,
        LocalDate birthday
) {
}