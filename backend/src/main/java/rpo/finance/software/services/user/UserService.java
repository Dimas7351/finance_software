package rpo.finance.software.services.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rpo.finance.software.DTO.user.PasswordRequest;
import rpo.finance.software.DTO.user.UserInfoDTO;
import rpo.finance.software.entities.User;
import rpo.finance.software.exceptions.InvalidCredentialsException;
import rpo.finance.software.exceptions.UserNotFoundException;
import rpo.finance.software.mappers.ClientInfoMapper;
import rpo.finance.software.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ClientInfoMapper clientInfoMapper;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, ClientInfoMapper clientInfoMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientInfoMapper = clientInfoMapper;
        this.passwordEncoder = passwordEncoder;
    }
    public UserInfoDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("Пользователь с идентификатором " + id + " не найден."));

        return clientInfoMapper.apply(user);
    }

    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }else {
            throw new UserNotFoundException("Пользователь с идентификатором " + id + " не найден.");
        }
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

}
