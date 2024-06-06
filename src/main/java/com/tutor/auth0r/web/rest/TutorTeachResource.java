package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.TutorTeachRepository;
import com.tutor.auth0r.service.TutorTeachService;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.TutorTeach}.
 */
@RestController
@RequestMapping("/api/tutor-teaches")
public class TutorTeachResource {

    private final Logger log = LoggerFactory.getLogger(TutorTeachResource.class);

    private static final String ENTITY_NAME = "tutorTeach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorTeachService tutorTeachService;

    private final TutorTeachRepository tutorTeachRepository;

    public TutorTeachResource(TutorTeachService tutorTeachService, TutorTeachRepository tutorTeachRepository) {
        this.tutorTeachService = tutorTeachService;
        this.tutorTeachRepository = tutorTeachRepository;
    }

    /**
     * {@code POST  /tutor-teaches} : Create a new tutorTeach.
     *
     * @param tutorTeachDTO the tutorTeachDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorTeachDTO, or with status {@code 400 (Bad Request)} if the tutorTeach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TutorTeachDTO> createTutorTeach(@RequestBody TutorTeachDTO tutorTeachDTO) throws URISyntaxException {
        log.debug("REST request to save TutorTeach : {}", tutorTeachDTO);
        if (tutorTeachDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutorTeach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tutorTeachDTO = tutorTeachService.save(tutorTeachDTO);
        return ResponseEntity.created(new URI("/api/tutor-teaches/" + tutorTeachDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tutorTeachDTO.getId().toString()))
            .body(tutorTeachDTO);
    }

    /**
     * {@code PUT  /tutor-teaches/:id} : Updates an existing tutorTeach.
     *
     * @param id the id of the tutorTeachDTO to save.
     * @param tutorTeachDTO the tutorTeachDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorTeachDTO,
     * or with status {@code 400 (Bad Request)} if the tutorTeachDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutorTeachDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TutorTeachDTO> updateTutorTeach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorTeachDTO tutorTeachDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TutorTeach : {}, {}", id, tutorTeachDTO);
        if (tutorTeachDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorTeachDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorTeachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tutorTeachDTO = tutorTeachService.update(tutorTeachDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorTeachDTO.getId().toString()))
            .body(tutorTeachDTO);
    }

    /**
     * {@code PATCH  /tutor-teaches/:id} : Partial updates given fields of an existing tutorTeach, field will ignore if it is null
     *
     * @param id the id of the tutorTeachDTO to save.
     * @param tutorTeachDTO the tutorTeachDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorTeachDTO,
     * or with status {@code 400 (Bad Request)} if the tutorTeachDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tutorTeachDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tutorTeachDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TutorTeachDTO> partialUpdateTutorTeach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorTeachDTO tutorTeachDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TutorTeach partially : {}, {}", id, tutorTeachDTO);
        if (tutorTeachDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorTeachDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorTeachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TutorTeachDTO> result = tutorTeachService.partialUpdate(tutorTeachDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorTeachDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tutor-teaches} : get all the tutorTeaches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutorTeaches in body.
     */
    @GetMapping("")
    public List<TutorTeachDTO> getAllTutorTeaches() {
        log.debug("REST request to get all TutorTeaches");
        return tutorTeachService.findAll();
    }

    /**
     * {@code GET  /tutor-teaches/:id} : get the "id" tutorTeach.
     *
     * @param id the id of the tutorTeachDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutorTeachDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TutorTeachDTO> getTutorTeach(@PathVariable("id") Long id) {
        log.debug("REST request to get TutorTeach : {}", id);
        Optional<TutorTeachDTO> tutorTeachDTO = tutorTeachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tutorTeachDTO);
    }

    /**
     * {@code DELETE  /tutor-teaches/:id} : delete the "id" tutorTeach.
     *
     * @param id the id of the tutorTeachDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorTeach(@PathVariable("id") Long id) {
        log.debug("REST request to delete TutorTeach : {}", id);
        tutorTeachService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
