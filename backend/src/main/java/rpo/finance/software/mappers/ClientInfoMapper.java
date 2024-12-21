package rpo.finance.software.mappers;

import org.springframework.stereotype.Service;
import rpo.finance.software.DTO.user.UserInfoDTO;
import rpo.finance.software.entities.User;

import java.util.function.Function;

@Service
public class ClientInfoMapper implements Function<User, UserInfoDTO> {
    @Override
    public UserInfoDTO apply(User user) {
        return new UserInfoDTO(
                user.getUserID(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBirthday()
        );
    }
}
