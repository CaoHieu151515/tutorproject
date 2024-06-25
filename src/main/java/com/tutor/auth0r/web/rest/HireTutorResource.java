package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.HireTutorRepository;
import com.tutor.auth0r.service.HireTutorService;
import com.tutor.auth0r.service.dto.HireTutorDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.HireTutor}.
 */
@RestController
@RequestMapping("/api/hire-tutors")
public class HireTutorResource {

    private final Logger log = LoggerFactory.getLogger(HireTutorResource.class);

    private static final String ENTITY_NAME = "hireTutor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HireTutorService hireTutorService;

    private final HireTutorRepository hireTutorRepository;

    public HireTutorResource(HireTutorService hireTutorService, HireTutorRepository hireTutorRepository) {
        this.hireTutorService = hireTutorService;
        this.hireTutorRepository = hireTutorRepository;
    }

    /**
     * {@code POST  /hire-tutors} : Create a new hireTutor.
     *
     * @param hireTutorDTO the hireTutorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hireTutorDTO, or with status {@code 400 (Bad Request)} if the hireTutor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HireTutorDTO> createHireTutor(@RequestBody HireTutorDTO hireTutorDTO) throws URISyntaxException {
        log.debug("REST request to save HireTutor : {}", hireTutorDTO);
        if (hireTutorDTO.getId() != null) {
            throw new BadRequestAlertException("A new hireTutor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hireTutorDTO = hireTutorService.save(hireTutorDTO);
        return ResponseEntity.created(new URI("/api/hire-tutors/" + hireTutorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hireTutorDTO.getId().toString()))
            .body(hireTutorDTO);
    }

    @PostMapping("/hireTutor")
    public ResponseEntity<HireTutorDTO> HireTutor(@RequestBody HireTutorDTO hireTutorDTO) throws URISyntaxException {
        log.debug("REST request to save HireTutor : {}", hireTutorDTO);
        hireTutorDTO = hireTutorService.Hire(hireTutorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hireTutorDTO.getId().toString()))
            .body(hireTutorDTO);
    }

    /**
     * {@code PUT  /hire-tutors/:id} : Updates an existing hireTutor.
     *
     * @param id the id of the hireTutorDTO to save.
     * @param hireTutorDTO the hireTutorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hireTutorDTO,
     * or with status {@code 400 (Bad Request)} if the hireTutorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hireTutorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HireTutorDTO> updateHireTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HireTutorDTO hireTutorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HireTutor : {}, {}", id, hireTutorDTO);
        if (hireTutorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hireTutorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hireTutorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hireTutorDTO = hireTutorService.update(hireTutorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hireTutorDTO.getId().toString()))
            .body(hireTutorDTO);
    }

    /**
     * {@code PATCH  /hire-tutors/:id} : Partial updates given fields of an existing hireTutor, field will ignore if it is null
     *
     * @param id the id of the hireTutorDTO to save.
     * @param hireTutorDTO the hireTutorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hireTutorDTO,
     * or with status {@code 400 (Bad Request)} if the hireTutorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hireTutorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hireTutorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HireTutorDTO> partialUpdateHireTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HireTutorDTO hireTutorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HireTutor partially : {}, {}", id, hireTutorDTO);
        if (hireTutorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hireTutorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hireTutorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HireTutorDTO> result = hireTutorService.partialUpdate(hireTutorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hireTutorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hire-tutors} : get all the hireTutors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hireTutors in body.
     */
    @GetMapping("")
    public List<HireTutorDTO> getAllHireTutors() {
        log.debug("REST request to get all HireTutors");
        return hireTutorService.findAll();
    }

    /**
     * {@code GET  /hire-tutors/:id} : get the "id" hireTutor.
     *
     * @param id the id of the hireTutorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hireTutorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HireTutorDTO> getHireTutor(@PathVariable("id") Long id) {
        log.debug("REST request to get HireTutor : {}", id);
        Optional<HireTutorDTO> hireTutorDTO = hireTutorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hireTutorDTO);
    }

    /**
     * {@code DELETE  /hire-tutors/:id} : delete the "id" hireTutor.
     *
     * @param id the id of the hireTutorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHireTutor(@PathVariable("id") Long id) {
        log.debug("REST request to delete HireTutor : {}", id);
        hireTutorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<Void> updateHireTutorStatus(@PathVariable(value = "id", required = false) final Long id)
        throws URISyntaxException {
        hireTutorService.updatesTatus(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
