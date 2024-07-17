package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.enumeration.Teach;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tutor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByStatus(TuStatus status);

    @Query("SELECT t FROM Tutor t JOIN t.tutorDetails td JOIN td.tutorTeaches tt WHERE tt.subject IN :subjects")
    List<Tutor> findBySubjects(@Param("subjects") List<Teach> subjects);

    Optional<Tutor> findByAppUser(AppUser appUser);

    @Query("SELECT t FROM Tutor t JOIN FETCH t.appUser au JOIN FETCH au.user u WHERE u.login = :login")
    Optional<Tutor> findTutorByUserLogin(@Param("login") String login);

    @Query("SELECT t FROM Tutor t JOIN t.tutorDetails td JOIN td.tutorTeaches tt WHERE tt.subject IN :subjects AND t.id <> :id")
    List<Tutor> findBySubjectsAndIdNot(@Param("subjects") List<Teach> subjects, @Param("id") Long id);
}
