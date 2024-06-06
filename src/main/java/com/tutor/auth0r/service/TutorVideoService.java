package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.TutorVideoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.TutorVideo}.
 */
public interface TutorVideoService {
    /**
     * Save a tutorVideo.
     *
     * @param tutorVideoDTO the entity to save.
     * @return the persisted entity.
     */
    TutorVideoDTO save(TutorVideoDTO tutorVideoDTO);

    /**
     * Updates a tutorVideo.
     *
     * @param tutorVideoDTO the entity to update.
     * @return the persisted entity.
     */
    TutorVideoDTO update(TutorVideoDTO tutorVideoDTO);

    /**
     * Partially updates a tutorVideo.
     *
     * @param tutorVideoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TutorVideoDTO> partialUpdate(TutorVideoDTO tutorVideoDTO);

    /**
     * Get all the tutorVideos.
     *
     * @return the list of entities.
     */
    List<TutorVideoDTO> findAll();

    /**
     * Get all the TutorVideoDTO where TutorDetails is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TutorVideoDTO> findAllWhereTutorDetailsIsNull();

    /**
     * Get the "id" tutorVideo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TutorVideoDTO> findOne(Long id);

    /**
     * Delete the "id" tutorVideo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
