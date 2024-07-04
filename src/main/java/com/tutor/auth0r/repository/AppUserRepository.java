package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query("SELECT au FROM AppUser au WHERE au.tutor.recommend = true")
    List<AppUser> findAllAppUsersWithRecommendedTutors();

    AppUser findByUser(User user);

    List<AppUser> findByTutorStatus(TuStatus status);
}
