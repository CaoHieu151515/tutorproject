package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.HireTutorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.HireTutor}.
 */
public interface HireTutorService {
    /**
     * Save a hireTutor.
     *
     * @param hireTutorDTO the entity to save.
     * @return the persisted entity.
     */
    HireTutorDTO save(HireTutorDTO hireTutorDTO);

    HireTutorDTO Hire(HireTutorDTO hireTutorDTO);

    /**
     * Updates a hireTutor.
     *
     * @param hireTutorDTO the entity to update.
     * @return the persisted entity.
     */
    HireTutorDTO update(HireTutorDTO hireTutorDTO);

    HireTutorDTO updatesTatus(Long id);

    HireTutorDTO updatesTatusCancel(Long id);

    /**
     * Partially updates a hireTutor.
     *
     * @param hireTutorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HireTutorDTO> partialUpdate(HireTutorDTO hireTutorDTO);

    /**
     * Get all the hireTutors.
     *
     * @return the list of entities.
     */
    List<HireTutorDTO> findAll();

    /**
     * Get the "id" hireTutor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HireTutorDTO> findOne(Long id);

    /**
     * Delete the "id" hireTutor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
