package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.ThirdPartyTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ThirdPartyTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThirdPartyTransactionRepository extends JpaRepository<ThirdPartyTransaction, Long> {}
