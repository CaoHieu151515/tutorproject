package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.TutorImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TutorImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorImageRepository extends JpaRepository<TutorImage, Long> {}
