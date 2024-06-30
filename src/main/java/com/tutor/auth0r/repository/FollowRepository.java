package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Follow;
import com.tutor.auth0r.domain.Tutor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Follow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAppUserAndFollowedTutor(AppUser appUser, Tutor tutor);

    List<Follow> findByFollowerAppUser(AppUser appUser);
}
