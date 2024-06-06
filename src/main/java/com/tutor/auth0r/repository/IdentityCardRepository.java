package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.IdentityCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IdentityCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentityCardRepository extends JpaRepository<IdentityCard, Long> {}
