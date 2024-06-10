package com.tutor.auth0r.repository;

import com.tutor.auth0r.domain.UserVerify;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserVerify entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserVerifyRepository extends JpaRepository<UserVerify, Long> {}
