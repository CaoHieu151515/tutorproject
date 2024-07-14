package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WalletTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    @Query(
        "SELECT wt FROM WalletTransaction wt WHERE wt.wallet.id = :walletId AND wt.createAt BETWEEN :startDate AND :endDate AND wt.type = 'SERVICE_FEE_EARN'"
    )
    List<WalletTransaction> findAllByWalletIdAndCreateAtBetween(
        @Param("walletId") Long walletId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    List<WalletTransaction> findByType(WalletTransactionType type);
}
