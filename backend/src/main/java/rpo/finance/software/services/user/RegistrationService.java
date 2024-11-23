package rpo.finance.software.services.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rpo.finance.software.DTO.user.UserRegistrationDTO;
import rpo.finance.software.entities.ConfirmationToken;
import rpo.finance.software.entities.User;
import rpo.finance.software.exceptions.AccountAlreadyVerifiedException;
import rpo.finance.software.exceptions.EmailAlreadyExistException;
import rpo.finance.software.exceptions.InvalidTokenException;
import rpo.finance.software.exceptions.TokenExpiredException;
import rpo.finance.software.mappers.ClientRegistrationMapper;
import rpo.finance.software.repositories.UserRepository;
import rpo.finance.software.services.email.ConfirmationTokenService;
import rpo.finance.software.services.email.EmailService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class RegistrationService {
    private final ClientRegistrationMapper clientRegistrationMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;
    private final EmailService emailService;


    public void saveUser(User user) {
        userRepository.save(user);
    }
    @Transactional
    public void registerUser(UserRegistrationDTO userDTO) {
        if (userRepository.findUserByEmail(userDTO.email()).isPresent()) {
            throw new EmailAlreadyExistException("Пользователь с таким email уже существует.");
        }
        User user = clientRegistrationMapper.apply(userDTO);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        ConfirmationToken token = tokenService.createToken(user);

        emailService.sendVerificationMail(user.getEmail(),token.getToken());
    }

    @Transactional
    public String verifyAccount(String token) {
        ConfirmationToken confirmationToken = tokenService.getToken(token)
                .orElseThrow(() -> new InvalidTokenException("Неверный токен."));

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Срок действия токена истёк. Пожалуйста, запросите новую ссылку для подтверждения.");
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new AccountAlreadyVerifiedException("Аккаунт уже подтверждён.");
        }

        tokenService.setConfirmedAt(token);

        User user = confirmationToken.getUser();
        user.setIsVerified(true);
        saveUser(user);

        return "Аккаунт успешно подтверждён!";
    }

    @Transactional
    public void resendVerificationToken(String email){
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        if (user.getIsVerified()) {
            throw new AccountAlreadyVerifiedException("Аккаунт уже подтверждён.");
        }
        tokenService.invalidateExistingTokens(user);
        ConfirmationToken newToken = tokenService.createToken(user);
        emailService.sendVerificationMail(user.getEmail(),newToken.getToken());
    }
}