package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.TutorDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TutorDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorDetailsRepository extends JpaRepository<TutorDetails, Long> {}
