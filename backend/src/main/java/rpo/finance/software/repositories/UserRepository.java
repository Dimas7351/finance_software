package rpo.finance.software.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rpo.finance.software.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isVerified = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
