package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.HiringHoursRepository;
import com.tutor.auth0r.service.HiringHoursService;
import com.tutor.auth0r.service.dto.HiringHoursDTO;
import com.tutor.auth0r.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.HiringHours}.
 */
@RestController
@RequestMapping("/api/hiring-hours")
public class HiringHoursResource {

    private static final Logger log = LoggerFactory.getLogger(HiringHoursResource.class);

    private static final String ENTITY_NAME = "hiringHours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HiringHoursService hiringHoursService;

    private final HiringHoursRepository hiringHoursRepository;

    public HiringHoursResource(HiringHoursService hiringHoursService, HiringHoursRepository hiringHoursRepository) {
        this.hiringHoursService = hiringHoursService;
        this.hiringHoursRepository = hiringHoursRepository;
    }

    /**
     * {@code POST  /hiring-hours} : Create a new hiringHours.
     *
     * @param hiringHoursDTO the hiringHoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hiringHoursDTO, or with status {@code 400 (Bad Request)} if the hiringHours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HiringHoursDTO> createHiringHours(@Valid @RequestBody HiringHoursDTO hiringHoursDTO) throws URISyntaxException {
        log.debug("REST request to save HiringHours : {}", hiringHoursDTO);
        if (hiringHoursDTO.getId() != null) {
            throw new BadRequestAlertException("A new hiringHours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hiringHoursDTO = hiringHoursService.save(hiringHoursDTO);
        return ResponseEntity.created(new URI("/api/hiring-hours/" + hiringHoursDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hiringHoursDTO.getId().toString()))
            .body(hiringHoursDTO);
    }

    /**
     * {@code PUT  /hiring-hours/:id} : Updates an existing hiringHours.
     *
     * @param id the id of the hiringHoursDTO to save.
     * @param hiringHoursDTO the hiringHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hiringHoursDTO,
     * or with status {@code 400 (Bad Request)} if the hiringHoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hiringHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HiringHoursDTO> updateHiringHours(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HiringHoursDTO hiringHoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HiringHours : {}, {}", id, hiringHoursDTO);
        if (hiringHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hiringHoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hiringHoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hiringHoursDTO = hiringHoursService.update(hiringHoursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hiringHoursDTO.getId().toString()))
            .body(hiringHoursDTO);
    }

    /**
     * {@code PATCH  /hiring-hours/:id} : Partial updates given fields of an existing hiringHours, field will ignore if it is null
     *
     * @param id the id of the hiringHoursDTO to save.
     * @param hiringHoursDTO the hiringHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hiringHoursDTO,
     * or with status {@code 400 (Bad Request)} if the hiringHoursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hiringHoursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hiringHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HiringHoursDTO> partialUpdateHiringHours(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HiringHoursDTO hiringHoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HiringHours partially : {}, {}", id, hiringHoursDTO);
        if (hiringHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hiringHoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hiringHoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HiringHoursDTO> result = hiringHoursService.partialUpdate(hiringHoursDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hiringHoursDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hiring-hours} : get all the hiringHours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hiringHours in body.
     */
    @GetMapping("")
    public List<HiringHoursDTO> getAllHiringHours() {
        log.debug("REST request to get all HiringHours");
        return hiringHoursService.findAll();
    }

    /**
     * {@code GET  /hiring-hours/:id} : get the "id" hiringHours.
     *
     * @param id the id of the hiringHoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hiringHoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HiringHoursDTO> getHiringHours(@PathVariable("id") Long id) {
        log.debug("REST request to get HiringHours : {}", id);
        Optional<HiringHoursDTO> hiringHoursDTO = hiringHoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hiringHoursDTO);
    }

    /**
     * {@code DELETE  /hiring-hours/:id} : delete the "id" hiringHours.
     *
     * @param id the id of the hiringHoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHiringHours(@PathVariable("id") Long id) {
        log.debug("REST request to delete HiringHours : {}", id);
        hiringHoursService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
