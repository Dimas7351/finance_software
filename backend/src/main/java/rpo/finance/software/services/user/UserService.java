package rpo.finance.software.services.user;

import org.springframework.stereotype.Service;
import rpo.finance.software.DTO.user.Account;
import rpo.finance.software.entities.User;
import rpo.finance.software.exceptions.AccountNotFoundException;
import rpo.finance.software.mappers.AccountInfoMapper;
import rpo.finance.software.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountInfoMapper accountInfoMapper;

    public UserService(UserRepository userRepository, AccountInfoMapper accountInfoMapper) {
        this.userRepository = userRepository;
        this.accountInfoMapper = accountInfoMapper;
    }

    public Account getUserAccount(Long id) {
        return userRepository.findById(id)
                .map(accountInfoMapper)
                .orElseThrow(() -> new AccountNotFoundException("Invalid"));
    }

    public void deleteAccount(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found!"));
        userRepository.deleteById(id);
    }
}
