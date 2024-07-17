package com.tutor.auth0r.service;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.CustomDTO.AllRecommendDTO;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfConfirmingDTO;
import com.tutor.auth0r.service.dto.CustomDTO.TutorEditProfileDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UpdatecertificateDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UserProfileDTO;
import com.tutor.auth0r.service.dto.CustomDTO.WithdrawDTO;
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

    AppUser getBycurrentAppUser();
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

    AppUserDTO AdminRejectTutor(Long id);

    /**
     * Delete the "id" appUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<AllRecommendDTO> AllAppUsersWithRecommend();

    List<ListOfConfirmingDTO> GetAllConfirming();

    AppUserDTO updateVerify(AppUserDTO appUserDTO);

    Optional<UpdatecertificateDTO> findOneWithAllCetitycate();

    Optional<UpdatecertificateDTO> updateCertificate(UpdatecertificateDTO updateCertificateDTO);

    Optional<UserProfileDTO> findUserProfile();

    TutorEditProfileDTO findTutorProfile();

    TutorEditProfileDTO updateTutorProfile(TutorEditProfileDTO dto);

    Optional<UserProfileDTO> updateUserProfile(UserProfileDTO userProfileDTO);

    List<AllRecommendDTO> AllAppUsersWithSearch(String search);

    Optional<WithdrawDTO> WithdrawDetails();

    Optional<WithdrawDTO> CreateWithdrawApplication(WithdrawDTO withdrawDTO);
}
