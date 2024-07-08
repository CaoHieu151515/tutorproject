package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.UserVerifyRepository;
import com.tutor.auth0r.service.UserVerifyService;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.UserVerify}.
 */
@RestController
@RequestMapping("/api/user-verifies")
public class UserVerifyResource {

    private static final Logger log = LoggerFactory.getLogger(UserVerifyResource.class);

    private static final String ENTITY_NAME = "userVerify";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserVerifyService userVerifyService;

    private final UserVerifyRepository userVerifyRepository;

    public UserVerifyResource(UserVerifyService userVerifyService, UserVerifyRepository userVerifyRepository) {
        this.userVerifyService = userVerifyService;
        this.userVerifyRepository = userVerifyRepository;
    }

    /**
     * {@code POST  /user-verifies} : Create a new userVerify.
     *
     * @param userVerifyDTO the userVerifyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userVerifyDTO, or with status {@code 400 (Bad Request)} if the userVerify has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserVerifyDTO> createUserVerify(@RequestBody UserVerifyDTO userVerifyDTO) throws URISyntaxException {
        log.debug("REST request to save UserVerify : {}", userVerifyDTO);
        if (userVerifyDTO.getId() != null) {
            throw new BadRequestAlertException("A new userVerify cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userVerifyDTO = userVerifyService.save(userVerifyDTO);
        return ResponseEntity.created(new URI("/api/user-verifies/" + userVerifyDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, userVerifyDTO.getId().toString()))
            .body(userVerifyDTO);
    }

    /**
     * {@code PUT  /user-verifies/:id} : Updates an existing userVerify.
     *
     * @param id the id of the userVerifyDTO to save.
     * @param userVerifyDTO the userVerifyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userVerifyDTO,
     * or with status {@code 400 (Bad Request)} if the userVerifyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userVerifyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserVerifyDTO> updateUserVerify(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserVerifyDTO userVerifyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserVerify : {}, {}", id, userVerifyDTO);
        if (userVerifyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userVerifyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userVerifyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        userVerifyDTO = userVerifyService.update(userVerifyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userVerifyDTO.getId().toString()))
            .body(userVerifyDTO);
    }

    /**
     * {@code PATCH  /user-verifies/:id} : Partial updates given fields of an existing userVerify, field will ignore if it is null
     *
     * @param id the id of the userVerifyDTO to save.
     * @param userVerifyDTO the userVerifyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userVerifyDTO,
     * or with status {@code 400 (Bad Request)} if the userVerifyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userVerifyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userVerifyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserVerifyDTO> partialUpdateUserVerify(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserVerifyDTO userVerifyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserVerify partially : {}, {}", id, userVerifyDTO);
        if (userVerifyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userVerifyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userVerifyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserVerifyDTO> result = userVerifyService.partialUpdate(userVerifyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userVerifyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-verifies} : get all the userVerifies.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userVerifies in body.
     */
    @GetMapping("")
    public List<UserVerifyDTO> getAllUserVerifies(@RequestParam(name = "filter", required = false) String filter) {
        if ("appuser-is-null".equals(filter)) {
            log.debug("REST request to get all UserVerifys where appUser is null");
            return userVerifyService.findAllWhereAppUserIsNull();
        }
        log.debug("REST request to get all UserVerifies");
        return userVerifyService.findAll();
    }

    /**
     * {@code GET  /user-verifies/:id} : get the "id" userVerify.
     *
     * @param id the id of the userVerifyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userVerifyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserVerifyDTO> getUserVerify(@PathVariable("id") Long id) {
        log.debug("REST request to get UserVerify : {}", id);
        Optional<UserVerifyDTO> userVerifyDTO = userVerifyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userVerifyDTO);
    }

    /**
     * {@code DELETE  /user-verifies/:id} : delete the "id" userVerify.
     *
     * @param id the id of the userVerifyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserVerify(@PathVariable("id") Long id) {
        log.debug("REST request to delete UserVerify : {}", id);
        userVerifyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
