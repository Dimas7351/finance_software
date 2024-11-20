package rpo.finance.software.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rpo.finance.software.DTO.user.UserRegistrationDTO;
import rpo.finance.software.DTO.user.UserSignInDTO;
import rpo.finance.software.entities.User;
import rpo.finance.software.exceptions.user_exceptions.EmailAlreadyExistException;
import rpo.finance.software.exceptions.user_exceptions.InvalidCredentialsException;
import rpo.finance.software.mappers.ClientRegistrationMapper;
import rpo.finance.software.repositories.UserRepository;

@Service
public class UserService {
    private final ClientRegistrationMapper clientRegistrationMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(ClientRegistrationMapper clientRegistrationMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.clientRegistrationMapper = clientRegistrationMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegistrationDTO userDTO) {
        if (userRepository.findUserByEmail(userDTO.email()).isPresent()) {
            throw new EmailAlreadyExistException("Пользователь с таким email уже существует.");
        }
        User user = clientRegistrationMapper.apply(userDTO);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public String signin(UserSignInDTO userSignInDTO) {
        User user = userRepository.findUserByEmail(userSignInDTO.email())
                .orElseThrow(() -> new InvalidCredentialsException("Неверные учетные данные"));

        if (!passwordEncoder.matches(userSignInDTO.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Неверные учетные данные");
        }
        return "Успешный вход";
    }
}
