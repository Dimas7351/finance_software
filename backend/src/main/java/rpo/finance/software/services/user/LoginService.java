package rpo.finance.software.services.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rpo.finance.software.DTO.user.UserSignInDTO;
import rpo.finance.software.entities.User;
import rpo.finance.software.exceptions.InvalidCredentialsException;
import rpo.finance.software.exceptions.AccountNotVerifiedException;
import rpo.finance.software.repositories.UserRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signin(UserSignInDTO userSignInDTO) {
        User user = userRepository.findUserByEmail(userSignInDTO.email())
                .orElseThrow(() -> new InvalidCredentialsException("Неверные учетные данные"));

        if(!user.getIsVerified()){
            throw new AccountNotVerifiedException("verify your account please");
        }

        if (!passwordEncoder.matches(userSignInDTO.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Неверные учетные данные");
        }
        return "Успешный вход";
    }
}
