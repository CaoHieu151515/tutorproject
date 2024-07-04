package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tutor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByStatus(TuStatus status);
}
