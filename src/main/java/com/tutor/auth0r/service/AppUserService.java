package com.tutor.auth0r.service;

import com.tutor.auth0r.service.dto.AppUserDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.AppUser}.
 */
public interface AppUserService {
    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save.
     * @return the persisted entity.
     */
    AppUserDTO save(AppUserDTO appUserDTO);

    Optional<AppUserDTO> getBycurrentUser();

    /**
     * Updates a appUser.
     *
     * @param appUserDTO the entity to update.
     * @return the persisted entity.
     */
    AppUserDTO update(AppUserDTO appUserDTO);

    /**
     * Partially updates a appUser.
     *
     * @param appUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppUserDTO> partialUpdate(AppUserDTO appUserDTO);

    /**
     * Get all the appUsers.
     *
     * @return the list of entities.
     */
    List<AppUserDTO> findAll();

    /**
     * Get all the AppUserDTO where Wallet is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AppUserDTO> findAllWhereWalletIsNull();

    /**
     * Get the "id" appUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppUserDTO> findOne(Long id);

    AppUserDTO AdminConFirmTutor(Long id);

    /**
     * Delete the "id" appUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<AppUserDTO> AllAppUsersWithRecommend();

    AppUserDTO updateVerify(AppUserDTO appUserDTO);
}
