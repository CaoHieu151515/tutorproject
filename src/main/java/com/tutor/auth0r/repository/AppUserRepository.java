package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
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

    Optional<AppUser> findOneByUser(User user);

    @Query("SELECT au FROM AppUser au WHERE au.tutor.status = 'READY'")
    List<AppUser> findAllAppUsersWithTutorStatusReady();

    @Query(
        "SELECT au FROM AppUser au JOIN au.user u WHERE (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND au.beTutor = true"
    )
    List<AppUser> findByUserFirstNameOrLastNameContainingAndBeTutorTrue(String searchTerm);

    List<AppUser> findByBeTutorTrue();

    @Query("SELECT au FROM AppUser au WHERE au.beTutor = TRUE AND au.id <> :id")
    List<AppUser> findByBeTutorTrueAndIdNot(@Param("id") Long id);

    @Query(
        "SELECT au FROM AppUser au JOIN au.user u WHERE (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND au.beTutor = true AND au.id <> :id"
    )
    List<AppUser> findByUserFirstNameOrLastNameContainingAndBeTutorTrueAndIdNot(
        @Param("searchTerm") String searchTerm,
        @Param("id") Long id
    );

    @Query("SELECT au FROM AppUser au WHERE au.tutor.status = 'READY' AND au.id <> :id")
    List<AppUser> findAllAppUsersWithTutorStatusReadyAndIdNot(@Param("id") Long id);

    @Query("SELECT a FROM AppUser a WHERE a.user.login <> :login AND a.user.activated = true")
    List<AppUser> findAllByUserLoginNotAndActivated(@Param("login") String login);
}
