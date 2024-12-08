package rpo.finance.software.services.email;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rpo.finance.software.entities.ConfirmationToken;
import rpo.finance.software.entities.User;
import rpo.finance.software.repositories.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository tokenRepository;

    public ConfirmationToken createToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(20);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, createdAt, expiresAt, user);

        return tokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token){
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token invalid"));
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        tokenRepository.save(confirmationToken);
    }

    public void invalidateExistingTokens(User user) {
         tokenRepository.findAllByUser(user).forEach(token -> {
             token.setExpiresAt(LocalDateTime.now().minusSeconds(1));
             tokenRepository.save(token);
         });
    }
}
