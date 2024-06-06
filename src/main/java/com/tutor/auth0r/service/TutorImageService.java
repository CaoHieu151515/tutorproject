package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.TutorImageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.TutorImage}.
 */
public interface TutorImageService {
    /**
     * Save a tutorImage.
     *
     * @param tutorImageDTO the entity to save.
     * @return the persisted entity.
     */
    TutorImageDTO save(TutorImageDTO tutorImageDTO);

    /**
     * Updates a tutorImage.
     *
     * @param tutorImageDTO the entity to update.
     * @return the persisted entity.
     */
    TutorImageDTO update(TutorImageDTO tutorImageDTO);

    /**
     * Partially updates a tutorImage.
     *
     * @param tutorImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TutorImageDTO> partialUpdate(TutorImageDTO tutorImageDTO);

    /**
     * Get all the tutorImages.
     *
     * @return the list of entities.
     */
    List<TutorImageDTO> findAll();

    /**
     * Get the "id" tutorImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TutorImageDTO> findOne(Long id);

    /**
     * Delete the "id" tutorImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
