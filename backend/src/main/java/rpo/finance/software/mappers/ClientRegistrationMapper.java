package rpo.finance.software.mappers;

import org.springframework.stereotype.Service;
import rpo.finance.software.DTO.user.UserRegistrationDTO;
import rpo.finance.software.entities.User;

import java.util.function.Function;

@Service
public class ClientRegistrationMapper implements Function<UserRegistrationDTO, User> {

    @Override
    public User apply(UserRegistrationDTO userRegistrationDTO) {
        return User.builder()
                .name(userRegistrationDTO.name())
                .email(userRegistrationDTO.email())
                .password(userRegistrationDTO.password())
                .birthday(userRegistrationDTO.birthday())
                .phoneNumber(userRegistrationDTO.phoneNumber())
                .currency("USD")
                .isVerified(false)
                .build();
    }
}
