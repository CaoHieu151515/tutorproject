package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.TutorDetails}.
 */
public interface TutorDetailsService {
    /**
     * Save a tutorDetails.
     *
     * @param tutorDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    TutorDetailsDTO save(TutorDetailsDTO tutorDetailsDTO);

    /**
     * Updates a tutorDetails.
     *
     * @param tutorDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    TutorDetailsDTO update(TutorDetailsDTO tutorDetailsDTO);

    /**
     * Partially updates a tutorDetails.
     *
     * @param tutorDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TutorDetailsDTO> partialUpdate(TutorDetailsDTO tutorDetailsDTO);

    /**
     * Get all the tutorDetails.
     *
     * @return the list of entities.
     */
    List<TutorDetailsDTO> findAll();

    /**
     * Get all the TutorDetailsDTO where Tutor is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TutorDetailsDTO> findAllWhereTutorIsNull();

    /**
     * Get the "id" tutorDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TutorDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" tutorDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
