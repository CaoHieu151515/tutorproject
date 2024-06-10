package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.TutorImageRepository;
import com.tutor.auth0r.service.TutorImageService;
import com.tutor.auth0r.service.dto.TutorImageDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.TutorImage}.
 */
@RestController
@RequestMapping("/api/tutor-images")
public class TutorImageResource {

    private final Logger log = LoggerFactory.getLogger(TutorImageResource.class);

    private static final String ENTITY_NAME = "tutorImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorImageService tutorImageService;

    private final TutorImageRepository tutorImageRepository;

    public TutorImageResource(TutorImageService tutorImageService, TutorImageRepository tutorImageRepository) {
        this.tutorImageService = tutorImageService;
        this.tutorImageRepository = tutorImageRepository;
    }

    /**
     * {@code POST  /tutor-images} : Create a new tutorImage.
     *
     * @param tutorImageDTO the tutorImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorImageDTO, or with status {@code 400 (Bad Request)} if the tutorImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TutorImageDTO> createTutorImage(@RequestBody TutorImageDTO tutorImageDTO) throws URISyntaxException {
        log.debug("REST request to save TutorImage : {}", tutorImageDTO);
        if (tutorImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutorImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tutorImageDTO = tutorImageService.save(tutorImageDTO);
        return ResponseEntity.created(new URI("/api/tutor-images/" + tutorImageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tutorImageDTO.getId().toString()))
            .body(tutorImageDTO);
    }

    /**
     * {@code PUT  /tutor-images/:id} : Updates an existing tutorImage.
     *
     * @param id the id of the tutorImageDTO to save.
     * @param tutorImageDTO the tutorImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorImageDTO,
     * or with status {@code 400 (Bad Request)} if the tutorImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutorImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TutorImageDTO> updateTutorImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorImageDTO tutorImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TutorImage : {}, {}", id, tutorImageDTO);
        if (tutorImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tutorImageDTO = tutorImageService.update(tutorImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorImageDTO.getId().toString()))
            .body(tutorImageDTO);
    }

    /**
     * {@code PATCH  /tutor-images/:id} : Partial updates given fields of an existing tutorImage, field will ignore if it is null
     *
     * @param id the id of the tutorImageDTO to save.
     * @param tutorImageDTO the tutorImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorImageDTO,
     * or with status {@code 400 (Bad Request)} if the tutorImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tutorImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tutorImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TutorImageDTO> partialUpdateTutorImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorImageDTO tutorImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TutorImage partially : {}, {}", id, tutorImageDTO);
        if (tutorImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TutorImageDTO> result = tutorImageService.partialUpdate(tutorImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tutor-images} : get all the tutorImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutorImages in body.
     */
    @GetMapping("")
    public List<TutorImageDTO> getAllTutorImages() {
        log.debug("REST request to get all TutorImages");
        return tutorImageService.findAll();
    }

    /**
     * {@code GET  /tutor-images/:id} : get the "id" tutorImage.
     *
     * @param id the id of the tutorImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutorImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TutorImageDTO> getTutorImage(@PathVariable("id") Long id) {
        log.debug("REST request to get TutorImage : {}", id);
        Optional<TutorImageDTO> tutorImageDTO = tutorImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tutorImageDTO);
    }

    /**
     * {@code DELETE  /tutor-images/:id} : delete the "id" tutorImage.
     *
     * @param id the id of the tutorImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorImage(@PathVariable("id") Long id) {
        log.debug("REST request to delete TutorImage : {}", id);
        tutorImageService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
