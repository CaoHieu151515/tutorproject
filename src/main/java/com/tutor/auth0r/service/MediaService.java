package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.MediaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.Media}.
 */
public interface MediaService {
    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save.
     * @return the persisted entity.
     */
    MediaDTO save(MediaDTO mediaDTO);

    /**
     * Updates a media.
     *
     * @param mediaDTO the entity to update.
     * @return the persisted entity.
     */
    MediaDTO update(MediaDTO mediaDTO);

    /**
     * Partially updates a media.
     *
     * @param mediaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MediaDTO> partialUpdate(MediaDTO mediaDTO);

    /**
     * Get all the media.
     *
     * @return the list of entities.
     */
    List<MediaDTO> findAll();

    /**
     * Get all the MediaDTO where AcademicRank is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MediaDTO> findAllWhereAcademicRankIsNull();
    /**
     * Get all the MediaDTO where TutorImage is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MediaDTO> findAllWhereTutorImageIsNull();
    /**
     * Get all the MediaDTO where TutorVideo is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MediaDTO> findAllWhereTutorVideoIsNull();

    /**
     * Get the "id" media.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MediaDTO> findOne(Long id);

    /**
     * Delete the "id" media.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
