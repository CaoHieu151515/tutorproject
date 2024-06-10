package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.TutorVideo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TutorVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorVideoRepository extends JpaRepository<TutorVideo, Long> {}
