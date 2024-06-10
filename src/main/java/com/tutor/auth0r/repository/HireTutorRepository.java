package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.HireTutor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HireTutor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HireTutorRepository extends JpaRepository<HireTutor, Long> {}
