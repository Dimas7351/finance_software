package rpo.finance.software.mappers;

import org.springframework.stereotype.Service;
import rpo.finance.software.DTO.user.RegistrationStep1DTO;
import rpo.finance.software.entities.User;

import java.util.function.Function;

@Service
public class ClientRegistrationMapper implements Function<RegistrationStep1DTO, User> {

    @Override
    public User apply(RegistrationStep1DTO userRegistrationDTO) {
        return User.builder()
                .name(userRegistrationDTO.name())
                .email(userRegistrationDTO.email())
                .password(userRegistrationDTO.password())
                .isVerified(false)
                .build();
    }
}
