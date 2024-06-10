package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.TutorVideoRepository;
import com.tutor.auth0r.service.TutorVideoService;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.TutorVideo}.
 */
@RestController
@RequestMapping("/api/tutor-videos")
public class TutorVideoResource {

    private final Logger log = LoggerFactory.getLogger(TutorVideoResource.class);

    private static final String ENTITY_NAME = "tutorVideo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorVideoService tutorVideoService;

    private final TutorVideoRepository tutorVideoRepository;

    public TutorVideoResource(TutorVideoService tutorVideoService, TutorVideoRepository tutorVideoRepository) {
        this.tutorVideoService = tutorVideoService;
        this.tutorVideoRepository = tutorVideoRepository;
    }

    /**
     * {@code POST  /tutor-videos} : Create a new tutorVideo.
     *
     * @param tutorVideoDTO the tutorVideoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorVideoDTO, or with status {@code 400 (Bad Request)} if the tutorVideo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TutorVideoDTO> createTutorVideo(@RequestBody TutorVideoDTO tutorVideoDTO) throws URISyntaxException {
        log.debug("REST request to save TutorVideo : {}", tutorVideoDTO);
        if (tutorVideoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutorVideo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tutorVideoDTO = tutorVideoService.save(tutorVideoDTO);
        return ResponseEntity.created(new URI("/api/tutor-videos/" + tutorVideoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tutorVideoDTO.getId().toString()))
            .body(tutorVideoDTO);
    }

    /**
     * {@code PUT  /tutor-videos/:id} : Updates an existing tutorVideo.
     *
     * @param id the id of the tutorVideoDTO to save.
     * @param tutorVideoDTO the tutorVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorVideoDTO,
     * or with status {@code 400 (Bad Request)} if the tutorVideoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutorVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TutorVideoDTO> updateTutorVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorVideoDTO tutorVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TutorVideo : {}, {}", id, tutorVideoDTO);
        if (tutorVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tutorVideoDTO = tutorVideoService.update(tutorVideoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorVideoDTO.getId().toString()))
            .body(tutorVideoDTO);
    }

    /**
     * {@code PATCH  /tutor-videos/:id} : Partial updates given fields of an existing tutorVideo, field will ignore if it is null
     *
     * @param id the id of the tutorVideoDTO to save.
     * @param tutorVideoDTO the tutorVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorVideoDTO,
     * or with status {@code 400 (Bad Request)} if the tutorVideoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tutorVideoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tutorVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TutorVideoDTO> partialUpdateTutorVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorVideoDTO tutorVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TutorVideo partially : {}, {}", id, tutorVideoDTO);
        if (tutorVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TutorVideoDTO> result = tutorVideoService.partialUpdate(tutorVideoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorVideoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tutor-videos} : get all the tutorVideos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutorVideos in body.
     */
    @GetMapping("")
    public List<TutorVideoDTO> getAllTutorVideos(@RequestParam(name = "filter", required = false) String filter) {
        if ("tutordetails-is-null".equals(filter)) {
            log.debug("REST request to get all TutorVideos where tutorDetails is null");
            return tutorVideoService.findAllWhereTutorDetailsIsNull();
        }
        log.debug("REST request to get all TutorVideos");
        return tutorVideoService.findAll();
    }

    /**
     * {@code GET  /tutor-videos/:id} : get the "id" tutorVideo.
     *
     * @param id the id of the tutorVideoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutorVideoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TutorVideoDTO> getTutorVideo(@PathVariable("id") Long id) {
        log.debug("REST request to get TutorVideo : {}", id);
        Optional<TutorVideoDTO> tutorVideoDTO = tutorVideoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tutorVideoDTO);
    }

    /**
     * {@code DELETE  /tutor-videos/:id} : delete the "id" tutorVideo.
     *
     * @param id the id of the tutorVideoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorVideo(@PathVariable("id") Long id) {
        log.debug("REST request to delete TutorVideo : {}", id);
        tutorVideoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
