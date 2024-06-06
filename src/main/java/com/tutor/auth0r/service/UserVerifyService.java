package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.UserVerifyDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.UserVerify}.
 */
public interface UserVerifyService {
    /**
     * Save a userVerify.
     *
     * @param userVerifyDTO the entity to save.
     * @return the persisted entity.
     */
    UserVerifyDTO save(UserVerifyDTO userVerifyDTO);

    /**
     * Updates a userVerify.
     *
     * @param userVerifyDTO the entity to update.
     * @return the persisted entity.
     */
    UserVerifyDTO update(UserVerifyDTO userVerifyDTO);

    /**
     * Partially updates a userVerify.
     *
     * @param userVerifyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserVerifyDTO> partialUpdate(UserVerifyDTO userVerifyDTO);

    /**
     * Get all the userVerifies.
     *
     * @return the list of entities.
     */
    List<UserVerifyDTO> findAll();

    /**
     * Get all the UserVerifyDTO where AppUser is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UserVerifyDTO> findAllWhereAppUserIsNull();

    /**
     * Get the "id" userVerify.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserVerifyDTO> findOne(Long id);

    /**
     * Delete the "id" userVerify.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
