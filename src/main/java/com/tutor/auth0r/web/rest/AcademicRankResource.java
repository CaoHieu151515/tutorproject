package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.AcademicRankRepository;
import com.tutor.auth0r.service.AcademicRankService;
import com.tutor.auth0r.service.dto.AcademicRankDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.AcademicRank}.
 */
@RestController
@RequestMapping("/api/academic-ranks")
public class AcademicRankResource {

    private static final Logger log = LoggerFactory.getLogger(AcademicRankResource.class);

    private static final String ENTITY_NAME = "academicRank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicRankService academicRankService;

    private final AcademicRankRepository academicRankRepository;

    public AcademicRankResource(AcademicRankService academicRankService, AcademicRankRepository academicRankRepository) {
        this.academicRankService = academicRankService;
        this.academicRankRepository = academicRankRepository;
    }

    /**
     * {@code POST  /academic-ranks} : Create a new academicRank.
     *
     * @param academicRankDTO the academicRankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicRankDTO, or with status {@code 400 (Bad Request)} if the academicRank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AcademicRankDTO> createAcademicRank(@RequestBody AcademicRankDTO academicRankDTO) throws URISyntaxException {
        log.debug("REST request to save AcademicRank : {}", academicRankDTO);
        if (academicRankDTO.getId() != null) {
            throw new BadRequestAlertException("A new academicRank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        academicRankDTO = academicRankService.save(academicRankDTO);
        return ResponseEntity.created(new URI("/api/academic-ranks/" + academicRankDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, academicRankDTO.getId().toString()))
            .body(academicRankDTO);
    }

    /**
     * {@code PUT  /academic-ranks/:id} : Updates an existing academicRank.
     *
     * @param id the id of the academicRankDTO to save.
     * @param academicRankDTO the academicRankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicRankDTO,
     * or with status {@code 400 (Bad Request)} if the academicRankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicRankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AcademicRankDTO> updateAcademicRank(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicRankDTO academicRankDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AcademicRank : {}, {}", id, academicRankDTO);
        if (academicRankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicRankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicRankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        academicRankDTO = academicRankService.update(academicRankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicRankDTO.getId().toString()))
            .body(academicRankDTO);
    }

    /**
     * {@code PATCH  /academic-ranks/:id} : Partial updates given fields of an existing academicRank, field will ignore if it is null
     *
     * @param id the id of the academicRankDTO to save.
     * @param academicRankDTO the academicRankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicRankDTO,
     * or with status {@code 400 (Bad Request)} if the academicRankDTO is not valid,
     * or with status {@code 404 (Not Found)} if the academicRankDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the academicRankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcademicRankDTO> partialUpdateAcademicRank(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AcademicRankDTO academicRankDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcademicRank partially : {}, {}", id, academicRankDTO);
        if (academicRankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicRankDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicRankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcademicRankDTO> result = academicRankService.partialUpdate(academicRankDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicRankDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /academic-ranks} : get all the academicRanks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicRanks in body.
     */
    @GetMapping("")
    public List<AcademicRankDTO> getAllAcademicRanks() {
        log.debug("REST request to get all AcademicRanks");
        return academicRankService.findAll();
    }

    /**
     * {@code GET  /academic-ranks/:id} : get the "id" academicRank.
     *
     * @param id the id of the academicRankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicRankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AcademicRankDTO> getAcademicRank(@PathVariable("id") Long id) {
        log.debug("REST request to get AcademicRank : {}", id);
        Optional<AcademicRankDTO> academicRankDTO = academicRankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicRankDTO);
    }

    /**
     * {@code DELETE  /academic-ranks/:id} : delete the "id" academicRank.
     *
     * @param id the id of the academicRankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicRank(@PathVariable("id") Long id) {
        log.debug("REST request to delete AcademicRank : {}", id);
        academicRankService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
