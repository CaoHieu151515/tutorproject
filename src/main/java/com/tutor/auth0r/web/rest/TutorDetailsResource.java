package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.TutorDetailsRepository;
import com.tutor.auth0r.service.TutorDetailsService;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.TutorDetails}.
 */
@RestController
@RequestMapping("/api/tutor-details")
public class TutorDetailsResource {

    private static final Logger log = LoggerFactory.getLogger(TutorDetailsResource.class);

    private static final String ENTITY_NAME = "tutorDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorDetailsService tutorDetailsService;

    private final TutorDetailsRepository tutorDetailsRepository;

    public TutorDetailsResource(TutorDetailsService tutorDetailsService, TutorDetailsRepository tutorDetailsRepository) {
        this.tutorDetailsService = tutorDetailsService;
        this.tutorDetailsRepository = tutorDetailsRepository;
    }

    /**
     * {@code POST  /tutor-details} : Create a new tutorDetails.
     *
     * @param tutorDetailsDTO the tutorDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorDetailsDTO, or with status {@code 400 (Bad Request)} if the tutorDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TutorDetailsDTO> createTutorDetails(@RequestBody TutorDetailsDTO tutorDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save TutorDetails : {}", tutorDetailsDTO);
        if (tutorDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutorDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tutorDetailsDTO = tutorDetailsService.save(tutorDetailsDTO);
        return ResponseEntity.created(new URI("/api/tutor-details/" + tutorDetailsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tutorDetailsDTO.getId().toString()))
            .body(tutorDetailsDTO);
    }

    /**
     * {@code PUT  /tutor-details/:id} : Updates an existing tutorDetails.
     *
     * @param id the id of the tutorDetailsDTO to save.
     * @param tutorDetailsDTO the tutorDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the tutorDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutorDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TutorDetailsDTO> updateTutorDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorDetailsDTO tutorDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TutorDetails : {}, {}", id, tutorDetailsDTO);
        if (tutorDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tutorDetailsDTO = tutorDetailsService.update(tutorDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorDetailsDTO.getId().toString()))
            .body(tutorDetailsDTO);
    }

    /**
     * {@code PATCH  /tutor-details/:id} : Partial updates given fields of an existing tutorDetails, field will ignore if it is null
     *
     * @param id the id of the tutorDetailsDTO to save.
     * @param tutorDetailsDTO the tutorDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the tutorDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tutorDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tutorDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TutorDetailsDTO> partialUpdateTutorDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorDetailsDTO tutorDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TutorDetails partially : {}, {}", id, tutorDetailsDTO);
        if (tutorDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TutorDetailsDTO> result = tutorDetailsService.partialUpdate(tutorDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tutor-details} : get all the tutorDetails.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutorDetails in body.
     */
    @GetMapping("")
    public List<TutorDetailsDTO> getAllTutorDetails(@RequestParam(name = "filter", required = false) String filter) {
        if ("tutor-is-null".equals(filter)) {
            log.debug("REST request to get all TutorDetailss where tutor is null");
            return tutorDetailsService.findAllWhereTutorIsNull();
        }
        log.debug("REST request to get all TutorDetails");
        return tutorDetailsService.findAll();
    }

    /**
     * {@code GET  /tutor-details/:id} : get the "id" tutorDetails.
     *
     * @param id the id of the tutorDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutorDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TutorDetailsDTO> getTutorDetails(@PathVariable("id") Long id) {
        log.debug("REST request to get TutorDetails : {}", id);
        Optional<TutorDetailsDTO> tutorDetailsDTO = tutorDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tutorDetailsDTO);
    }

    /**
     * {@code DELETE  /tutor-details/:id} : delete the "id" tutorDetails.
     *
     * @param id the id of the tutorDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorDetails(@PathVariable("id") Long id) {
        log.debug("REST request to delete TutorDetails : {}", id);
        tutorDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
