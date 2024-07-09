package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.TuTorContactWithRepository;
import com.tutor.auth0r.service.TuTorContactWithService;
import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.TuTorContactWith}.
 */
@RestController
@RequestMapping("/api/tu-tor-contact-withs")
public class TuTorContactWithResource {

    private static final Logger log = LoggerFactory.getLogger(TuTorContactWithResource.class);

    private static final String ENTITY_NAME = "tuTorContactWith";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TuTorContactWithService tuTorContactWithService;

    private final TuTorContactWithRepository tuTorContactWithRepository;

    public TuTorContactWithResource(
        TuTorContactWithService tuTorContactWithService,
        TuTorContactWithRepository tuTorContactWithRepository
    ) {
        this.tuTorContactWithService = tuTorContactWithService;
        this.tuTorContactWithRepository = tuTorContactWithRepository;
    }

    /**
     * {@code POST  /tu-tor-contact-withs} : Create a new tuTorContactWith.
     *
     * @param tuTorContactWithDTO the tuTorContactWithDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tuTorContactWithDTO, or with status {@code 400 (Bad Request)} if the tuTorContactWith has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TuTorContactWithDTO> createTuTorContactWith(@RequestBody TuTorContactWithDTO tuTorContactWithDTO)
        throws URISyntaxException {
        log.debug("REST request to save TuTorContactWith : {}", tuTorContactWithDTO);
        if (tuTorContactWithDTO.getId() != null) {
            throw new BadRequestAlertException("A new tuTorContactWith cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tuTorContactWithDTO = tuTorContactWithService.save(tuTorContactWithDTO);
        return ResponseEntity.created(new URI("/api/tu-tor-contact-withs/" + tuTorContactWithDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tuTorContactWithDTO.getId().toString()))
            .body(tuTorContactWithDTO);
    }

    /**
     * {@code PUT  /tu-tor-contact-withs/:id} : Updates an existing tuTorContactWith.
     *
     * @param id the id of the tuTorContactWithDTO to save.
     * @param tuTorContactWithDTO the tuTorContactWithDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tuTorContactWithDTO,
     * or with status {@code 400 (Bad Request)} if the tuTorContactWithDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tuTorContactWithDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TuTorContactWithDTO> updateTuTorContactWith(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TuTorContactWithDTO tuTorContactWithDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TuTorContactWith : {}, {}", id, tuTorContactWithDTO);
        if (tuTorContactWithDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tuTorContactWithDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tuTorContactWithRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tuTorContactWithDTO = tuTorContactWithService.update(tuTorContactWithDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tuTorContactWithDTO.getId().toString()))
            .body(tuTorContactWithDTO);
    }

    /**
     * {@code PATCH  /tu-tor-contact-withs/:id} : Partial updates given fields of an existing tuTorContactWith, field will ignore if it is null
     *
     * @param id the id of the tuTorContactWithDTO to save.
     * @param tuTorContactWithDTO the tuTorContactWithDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tuTorContactWithDTO,
     * or with status {@code 400 (Bad Request)} if the tuTorContactWithDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tuTorContactWithDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tuTorContactWithDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TuTorContactWithDTO> partialUpdateTuTorContactWith(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TuTorContactWithDTO tuTorContactWithDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TuTorContactWith partially : {}, {}", id, tuTorContactWithDTO);
        if (tuTorContactWithDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tuTorContactWithDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tuTorContactWithRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TuTorContactWithDTO> result = tuTorContactWithService.partialUpdate(tuTorContactWithDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tuTorContactWithDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tu-tor-contact-withs} : get all the tuTorContactWiths.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tuTorContactWiths in body.
     */
    @GetMapping("")
    public List<TuTorContactWithDTO> getAllTuTorContactWiths() {
        log.debug("REST request to get all TuTorContactWiths");
        return tuTorContactWithService.findAll();
    }

    /**
     * {@code GET  /tu-tor-contact-withs/:id} : get the "id" tuTorContactWith.
     *
     * @param id the id of the tuTorContactWithDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tuTorContactWithDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TuTorContactWithDTO> getTuTorContactWith(@PathVariable("id") Long id) {
        log.debug("REST request to get TuTorContactWith : {}", id);
        Optional<TuTorContactWithDTO> tuTorContactWithDTO = tuTorContactWithService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tuTorContactWithDTO);
    }

    /**
     * {@code DELETE  /tu-tor-contact-withs/:id} : delete the "id" tuTorContactWith.
     *
     * @param id the id of the tuTorContactWithDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTuTorContactWith(@PathVariable("id") Long id) {
        log.debug("REST request to delete TuTorContactWith : {}", id);
        tuTorContactWithService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
