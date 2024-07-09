package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.TuTorContactWith;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TuTorContactWith entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TuTorContactWithRepository extends JpaRepository<TuTorContactWith, Long> {}
