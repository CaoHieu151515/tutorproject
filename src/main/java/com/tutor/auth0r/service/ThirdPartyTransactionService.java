package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.ThirdPartyTransactionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.ThirdPartyTransaction}.
 */
public interface ThirdPartyTransactionService {
    /**
     * Save a thirdPartyTransaction.
     *
     * @param thirdPartyTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    ThirdPartyTransactionDTO save(ThirdPartyTransactionDTO thirdPartyTransactionDTO);

    /**
     * Updates a thirdPartyTransaction.
     *
     * @param thirdPartyTransactionDTO the entity to update.
     * @return the persisted entity.
     */
    ThirdPartyTransactionDTO update(ThirdPartyTransactionDTO thirdPartyTransactionDTO);

    /**
     * Partially updates a thirdPartyTransaction.
     *
     * @param thirdPartyTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThirdPartyTransactionDTO> partialUpdate(ThirdPartyTransactionDTO thirdPartyTransactionDTO);

    /**
     * Get all the thirdPartyTransactions.
     *
     * @return the list of entities.
     */
    List<ThirdPartyTransactionDTO> findAll();

    /**
     * Get the "id" thirdPartyTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThirdPartyTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" thirdPartyTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
