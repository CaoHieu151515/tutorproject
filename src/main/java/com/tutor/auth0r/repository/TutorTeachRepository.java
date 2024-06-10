package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.TutorTeach;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TutorTeach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorTeachRepository extends JpaRepository<TutorTeach, Long> {}
