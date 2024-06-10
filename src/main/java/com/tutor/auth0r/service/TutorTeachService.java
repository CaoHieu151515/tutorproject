package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.TutorTeachDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.TutorTeach}.
 */
public interface TutorTeachService {
    /**
     * Save a tutorTeach.
     *
     * @param tutorTeachDTO the entity to save.
     * @return the persisted entity.
     */
    TutorTeachDTO save(TutorTeachDTO tutorTeachDTO);

    /**
     * Updates a tutorTeach.
     *
     * @param tutorTeachDTO the entity to update.
     * @return the persisted entity.
     */
    TutorTeachDTO update(TutorTeachDTO tutorTeachDTO);

    /**
     * Partially updates a tutorTeach.
     *
     * @param tutorTeachDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TutorTeachDTO> partialUpdate(TutorTeachDTO tutorTeachDTO);

    /**
     * Get all the tutorTeaches.
     *
     * @return the list of entities.
     */
    List<TutorTeachDTO> findAll();

    /**
     * Get the "id" tutorTeach.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TutorTeachDTO> findOne(Long id);

    /**
     * Delete the "id" tutorTeach.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
