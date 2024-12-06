package rpo.finance.software.DTO.user;

import java.time.LocalDate;

public record Account(
        Long id,
        String name,
        String phoneNumber,
        LocalDate birthday,
        String email
) {
}