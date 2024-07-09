package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.TuTorContactWith}.
 */
public interface TuTorContactWithService {
    /**
     * Save a tuTorContactWith.
     *
     * @param tuTorContactWithDTO the entity to save.
     * @return the persisted entity.
     */
    TuTorContactWithDTO save(TuTorContactWithDTO tuTorContactWithDTO);

    /**
     * Updates a tuTorContactWith.
     *
     * @param tuTorContactWithDTO the entity to update.
     * @return the persisted entity.
     */
    TuTorContactWithDTO update(TuTorContactWithDTO tuTorContactWithDTO);

    /**
     * Partially updates a tuTorContactWith.
     *
     * @param tuTorContactWithDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TuTorContactWithDTO> partialUpdate(TuTorContactWithDTO tuTorContactWithDTO);

    /**
     * Get all the tuTorContactWiths.
     *
     * @return the list of entities.
     */
    List<TuTorContactWithDTO> findAll();

    /**
     * Get the "id" tuTorContactWith.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TuTorContactWithDTO> findOne(Long id);

    /**
     * Delete the "id" tuTorContactWith.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
