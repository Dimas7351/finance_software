package rpo.finance.software.services.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rpo.finance.software.DTO.user.Account;
import rpo.finance.software.DTO.user.PasswordRequest;
import rpo.finance.software.entities.User;
import rpo.finance.software.exceptions.AccountNotFoundException;
import rpo.finance.software.exceptions.InvalidCredentialsException;
import rpo.finance.software.exceptions.UserNotFoundException;
import rpo.finance.software.mappers.AccountInfoMapper;
import rpo.finance.software.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountInfoMapper accountInfoMapper;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, AccountInfoMapper accountInfoMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountInfoMapper = accountInfoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getUserAccount(Long id) {
        return userRepository.findById(id)
                .map(accountInfoMapper)
                .orElseThrow(() -> new AccountNotFoundException("Пользователь с идентификатором " + id + " не найден."));
    }


    @Transactional
    public void changePassword(Long userId, PasswordRequest psq) {
        User user = userRepository.findById(userId).orElseThrow( () ->
                new UserNotFoundException("Пользователь с идентификатором " + userId + " не найден."));
        if (!passwordEncoder.matches(psq.oldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("error");
        }

        String hashedNewPassword = passwordEncoder.encode(psq.newPassword());
        user.setPassword(hashedNewPassword);

        userRepository.save(user);
    }


    public void deleteAccount(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Пользователь с идентификатором " + id + " не найден."));
        userRepository.deleteById(id);
    }
}
