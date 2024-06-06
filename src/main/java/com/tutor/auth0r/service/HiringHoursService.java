package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.HiringHoursDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.HiringHours}.
 */
public interface HiringHoursService {
    /**
     * Save a hiringHours.
     *
     * @param hiringHoursDTO the entity to save.
     * @return the persisted entity.
     */
    HiringHoursDTO save(HiringHoursDTO hiringHoursDTO);

    /**
     * Updates a hiringHours.
     *
     * @param hiringHoursDTO the entity to update.
     * @return the persisted entity.
     */
    HiringHoursDTO update(HiringHoursDTO hiringHoursDTO);

    /**
     * Partially updates a hiringHours.
     *
     * @param hiringHoursDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HiringHoursDTO> partialUpdate(HiringHoursDTO hiringHoursDTO);

    /**
     * Get all the hiringHours.
     *
     * @return the list of entities.
     */
    List<HiringHoursDTO> findAll();

    /**
     * Get the "id" hiringHours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HiringHoursDTO> findOne(Long id);

    /**
     * Delete the "id" hiringHours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
