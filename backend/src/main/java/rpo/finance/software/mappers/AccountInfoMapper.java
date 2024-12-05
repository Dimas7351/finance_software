package rpo.finance.software.mappers;

import org.springframework.stereotype.Service;
import rpo.finance.software.DTO.user.Account;
import rpo.finance.software.entities.User;

import java.util.function.Function;

@Service
public class AccountInfoMapper implements Function<User, Account> {

    @Override
    public Account apply(User user) {
        return new Account(user.getUserId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getBirthday(),
                user.getEmail()
        );
    }
}