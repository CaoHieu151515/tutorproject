package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.AcademicRank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AcademicRank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicRankRepository extends JpaRepository<AcademicRank, Long> {}
