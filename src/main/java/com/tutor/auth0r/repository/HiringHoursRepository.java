package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.HiringHours;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HiringHours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HiringHoursRepository extends JpaRepository<HiringHours, Long> {}
