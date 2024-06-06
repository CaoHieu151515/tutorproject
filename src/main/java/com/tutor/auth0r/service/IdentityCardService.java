package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.IdentityCardDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.IdentityCard}.
 */
public interface IdentityCardService {
    /**
     * Save a identityCard.
     *
     * @param identityCardDTO the entity to save.
     * @return the persisted entity.
     */
    IdentityCardDTO save(IdentityCardDTO identityCardDTO);

    /**
     * Updates a identityCard.
     *
     * @param identityCardDTO the entity to update.
     * @return the persisted entity.
     */
    IdentityCardDTO update(IdentityCardDTO identityCardDTO);

    /**
     * Partially updates a identityCard.
     *
     * @param identityCardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IdentityCardDTO> partialUpdate(IdentityCardDTO identityCardDTO);

    /**
     * Get all the identityCards.
     *
     * @return the list of entities.
     */
    List<IdentityCardDTO> findAll();

    /**
     * Get all the IdentityCardDTO where UserVerify is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<IdentityCardDTO> findAllWhereUserVerifyIsNull();

    /**
     * Get the "id" identityCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IdentityCardDTO> findOne(Long id);

    /**
     * Delete the "id" identityCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
