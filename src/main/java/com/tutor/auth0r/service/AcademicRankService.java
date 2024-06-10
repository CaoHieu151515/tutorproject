package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.AcademicRankDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.AcademicRank}.
 */
public interface AcademicRankService {
    /**
     * Save a academicRank.
     *
     * @param academicRankDTO the entity to save.
     * @return the persisted entity.
     */
    AcademicRankDTO save(AcademicRankDTO academicRankDTO);

    /**
     * Updates a academicRank.
     *
     * @param academicRankDTO the entity to update.
     * @return the persisted entity.
     */
    AcademicRankDTO update(AcademicRankDTO academicRankDTO);

    /**
     * Partially updates a academicRank.
     *
     * @param academicRankDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AcademicRankDTO> partialUpdate(AcademicRankDTO academicRankDTO);

    /**
     * Get all the academicRanks.
     *
     * @return the list of entities.
     */
    List<AcademicRankDTO> findAll();

    /**
     * Get the "id" academicRank.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicRankDTO> findOne(Long id);

    /**
     * Delete the "id" academicRank.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
