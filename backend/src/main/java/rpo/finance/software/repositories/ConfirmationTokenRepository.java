package rpo.finance.software.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rpo.finance.software.entities.ConfirmationToken;
import rpo.finance.software.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    List<ConfirmationToken> findAllByUser(User user);
}
