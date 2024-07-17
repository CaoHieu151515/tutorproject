package com.tutor.auth0r.service;

import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfTutorDTO;
import com.tutor.auth0r.service.dto.TuTorCusTomDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.Tutor}.
 */
public interface TutorService {
    /**
     * Save a tutor.
     *
     * @param tutorDTO the entity to save.
     * @return the persisted entity.
     */
    TutorDTO save(TutorDTO tutorDTO);

    Tutor save(Tutor tutor);

    /**
     * Updates a tutor.
     *
     * @param tutorDTO the entity to update.
     * @return the persisted entity.
     */
    TutorDTO update(TutorDTO tutorDTO);

    /**
     * Partially updates a tutor.
     *
     * @param tutorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TutorDTO> partialUpdate(TutorDTO tutorDTO);

    /**
     * Get all the tutors.
     *
     * @return the list of entities.
     */
    List<TutorDTO> findAll();

    /**
     * Get all the TutorDTO where AppUser is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TutorDTO> findAllWhereAppUserIsNull();

    /**
     * Get the "id" tutor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TutorDTO> findOne(Long id);

    Optional<TuTorCusTomDTO> findOneCustom(Long id);

    /**
     * Delete the "id" tutor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ListOfTutorDTO> getTutorsBySubject(String subject);

    void updateTutorStatusOnline(String login);

    void updateTutorStatusOffline(String login);

    void updateTutorStatusConFirming(String login);
}
