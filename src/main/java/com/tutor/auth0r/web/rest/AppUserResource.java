package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.AppUserRepository;
import com.tutor.auth0r.service.AppUserService;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.CustomDTO.AllRecommendDTO;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfConfirmingDTO;
import com.tutor.auth0r.service.dto.CustomDTO.ManageDTO;
import com.tutor.auth0r.service.dto.CustomDTO.TutorEditProfileDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UpdatecertificateDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UserProfileDTO;
import com.tutor.auth0r.service.dto.CustomDTO.WithdrawDTO;
import com.tutor.auth0r.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tutor.auth0r.domain.AppUser}.
 */
@RestController
@RequestMapping("/api/app-users")
public class AppUserResource {

    private static final Logger log = LoggerFactory.getLogger(AppUserResource.class);

    private static final String ENTITY_NAME = "appUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppUserService appUserService;

    private final AppUserRepository appUserRepository;

    public AppUserResource(AppUserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }

    /**
     * {@code POST  /app-users} : Create a new appUser.
     *
     * @param appUserDTO the appUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appUserDTO, or with status {@code 400 (Bad Request)} if the appUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppUserDTO> createAppUser(@RequestBody AppUserDTO appUserDTO) throws URISyntaxException {
        log.debug("REST request to save AppUser : {}", appUserDTO);
        if (appUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new appUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appUserDTO = appUserService.save(appUserDTO);
        return ResponseEntity.created(new URI("/api/app-users/" + appUserDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, appUserDTO.getId().toString()))
            .body(appUserDTO);
    }

    /**
     * {@code PUT  /app-users/:id} : Updates an existing appUser.
     *
     * @param id the id of the appUserDTO to save.
     * @param appUserDTO the appUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserDTO,
     * or with status {@code 400 (Bad Request)} if the appUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppUserDTO> updateAppUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserDTO appUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppUser : {}, {}", id, appUserDTO);
        if (appUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appUserDTO = appUserService.update(appUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appUserDTO.getId().toString()))
            .body(appUserDTO);
    }

    /**
     * {@code PATCH  /app-users/:id} : Partial updates given fields of an existing appUser, field will ignore if it is null
     *
     * @param id the id of the appUserDTO to save.
     * @param appUserDTO the appUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserDTO,
     * or with status {@code 400 (Bad Request)} if the appUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppUserDTO> partialUpdateAppUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserDTO appUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppUser partially : {}, {}", id, appUserDTO);
        if (appUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppUserDTO> result = appUserService.partialUpdate(appUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-users} : get all the appUsers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appUsers in body.
     */
    @GetMapping("")
    public List<AppUserDTO> getAllAppUsers(@RequestParam(name = "filter", required = false) String filter) {
        if ("wallet-is-null".equals(filter)) {
            log.debug("REST request to get all AppUsers where wallet is null");
            return appUserService.findAllWhereWalletIsNull();
        }
        log.debug("REST request to get all AppUsers");
        return appUserService.findAll();
    }

    @GetMapping("/GetAllRecommend")
    public List<AllRecommendDTO> getAllAppUsersWithRecommend() {
        return appUserService.AllAppUsersWithRecommend();
    }

    @GetMapping("/SearchTutor/{search}")
    public List<AllRecommendDTO> AllAppUsersWithSearch(@PathVariable("search") String search) {
        return appUserService.AllAppUsersWithSearch(search);
    }

    @GetMapping("/WithdrawForm")
    public ResponseEntity<WithdrawDTO> WithdrawForm() {
        Optional<WithdrawDTO> withdrawDTO = appUserService.WithdrawDetails();
        return ResponseUtil.wrapOrNotFound(withdrawDTO);
    }

    @PostMapping("/WithdrawForm/CreateApplication")
    public ResponseEntity<WithdrawDTO> CreateApplication(@RequestBody WithdrawDTO withdrawDTO) {
        Optional<WithdrawDTO> withdraw = appUserService.CreateWithdrawApplication(withdrawDTO);
        return ResponseUtil.wrapOrNotFound(withdraw);
    }

    /**
     * {@code GET  /app-users/:id} : get the "id" appUser.
     *
     * @param id the id of the appUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getAppUser(@PathVariable("id") Long id) {
        log.debug("REST request to get AppUser : {}", id);
        Optional<AppUserDTO> appUserDTO = appUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appUserDTO);
    }

    @GetMapping("/GetCurrentAppUser")
    public ResponseEntity<AppUserDTO> getCurrentAppUser() {
        log.debug("REST request to get AppUser : {}");
        Optional<AppUserDTO> appUserDTO = appUserService.getBycurrentUser();
        return ResponseUtil.wrapOrNotFound(appUserDTO);
    }

    @PostMapping("/{id}/ConFirmTutor")
    public ResponseEntity<AppUserDTO> AdminConFirmTutor(@PathVariable("id") Long id) {
        log.debug("REST request to get AppUser : {}", id);
        AppUserDTO appUserDTO = appUserService.AdminConFirmTutor(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appUserDTO.getId().toString()))
            .body(appUserDTO);
    }

    @PostMapping("/{id}/RejectTutor")
    public ResponseEntity<AppUserDTO> AdminRejectTutor(@PathVariable("id") Long id) {
        log.debug("REST request to get AppUser : {}", id);
        AppUserDTO appUserDTO = appUserService.AdminRejectTutor(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appUserDTO.getId().toString()))
            .body(appUserDTO);
    }

    /**
     * {@code DELETE  /app-users/:id} : delete the "id" appUser.
     *
     * @param id the id of the appUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppUser : {}", id);
        appUserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/{id}/updateAppUserVerify")
    public ResponseEntity<AppUserDTO> updateAppUserVerify(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserDTO appUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppUser : {}, {}", id, appUserDTO);
        if (appUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appUserDTO = appUserService.updateVerify(appUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appUserDTO.getId().toString()))
            .body(appUserDTO);
    }

    @GetMapping("/GetAllConfirming")
    public List<ListOfConfirmingDTO> GetAllConfirming() {
        log.debug("REST request to get all AppUsers");
        return appUserService.GetAllConfirming();
    }

    @GetMapping("/getAllCertifycate")
    public ResponseEntity<UpdatecertificateDTO> getAllCertifycate() {
        Optional<UpdatecertificateDTO> UpdatecertificateDTO = appUserService.findOneWithAllCetitycate();
        return ResponseUtil.wrapOrNotFound(UpdatecertificateDTO);
    }

    @PutMapping("/update-certificate")
    public ResponseEntity<UpdatecertificateDTO> updateCertificate(@RequestBody UpdatecertificateDTO updateCertificateDTO) {
        Optional<UpdatecertificateDTO> result = appUserService.updateCertificate(updateCertificateDTO);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @GetMapping("/getUserProfile")
    public ResponseEntity<UserProfileDTO> getUserProfile() {
        Optional<UserProfileDTO> UpdatecertificateDTO = appUserService.findUserProfile();
        return ResponseUtil.wrapOrNotFound(UpdatecertificateDTO);
    }

    @PutMapping("/updateUserProfile")
    public ResponseEntity<UserProfileDTO> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        Optional<UserProfileDTO> updatedUserProfileDTO = appUserService.updateUserProfile(userProfileDTO);
        return ResponseUtil.wrapOrNotFound(updatedUserProfileDTO);
    }

    @GetMapping("/getTutorProfile")
    public ResponseEntity<TutorEditProfileDTO> findTutorProfile() {
        TutorEditProfileDTO appUserDTO = appUserService.findTutorProfile();
        return ResponseEntity.ok(appUserDTO);
    }

    @PutMapping("/updateTutorProfile")
    public ResponseEntity<TutorEditProfileDTO> updateTutorProfile(@RequestBody TutorEditProfileDTO dto) {
        TutorEditProfileDTO updatedDTO = appUserService.updateTutorProfile(dto);
        return ResponseEntity.ok(updatedDTO);
    }
}
