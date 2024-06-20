package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.Wallet;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wallet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wallet from Wallet wallet where wallet.appUser.user.login = ?1")
    Optional<Wallet> findByUserLogin(String login);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wallet from Wallet wallet where wallet.appUser.user.login = ?#{authentication.name}")
    Optional<Wallet> findByUserIsCurrentUser();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wallet from Wallet wallet where wallet.appUser.user.login = 'admin'")
    Optional<Wallet> findByAdmin();
}
