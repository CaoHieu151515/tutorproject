package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.TutorService;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfTutorDTO;
import com.tutor.auth0r.service.dto.TuTorCusTomDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.Tutor}.
 */
@RestController
@RequestMapping("/api/tutors")
public class TutorResource {

    private static final Logger log = LoggerFactory.getLogger(TutorResource.class);

    private static final String ENTITY_NAME = "tutor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorService tutorService;

    private final TutorRepository tutorRepository;

    public TutorResource(TutorService tutorService, TutorRepository tutorRepository) {
        this.tutorService = tutorService;
        this.tutorRepository = tutorRepository;
    }

    /**
     * {@code POST  /tutors} : Create a new tutor.
     *
     * @param tutorDTO the tutorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorDTO, or with status {@code 400 (Bad Request)} if the tutor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TutorDTO> createTutor(@RequestBody TutorDTO tutorDTO) throws URISyntaxException {
        log.debug("REST request to save Tutor : {}", tutorDTO);
        if (tutorDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tutorDTO = tutorService.save(tutorDTO);
        return ResponseEntity.created(new URI("/api/tutors/" + tutorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tutorDTO.getId().toString()))
            .body(tutorDTO);
    }

    /**
     * {@code PUT  /tutors/:id} : Updates an existing tutor.
     *
     * @param id the id of the tutorDTO to save.
     * @param tutorDTO the tutorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorDTO,
     * or with status {@code 400 (Bad Request)} if the tutorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TutorDTO> updateTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorDTO tutorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tutor : {}, {}", id, tutorDTO);
        if (tutorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tutorDTO = tutorService.update(tutorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorDTO.getId().toString()))
            .body(tutorDTO);
    }

    /**
     * {@code PATCH  /tutors/:id} : Partial updates given fields of an existing tutor, field will ignore if it is null
     *
     * @param id the id of the tutorDTO to save.
     * @param tutorDTO the tutorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorDTO,
     * or with status {@code 400 (Bad Request)} if the tutorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tutorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tutorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TutorDTO> partialUpdateTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorDTO tutorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tutor partially : {}, {}", id, tutorDTO);
        if (tutorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TutorDTO> result = tutorService.partialUpdate(tutorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tutors} : get all the tutors.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutors in body.
     */
    @GetMapping("")
    public List<TutorDTO> getAllTutors(@RequestParam(name = "filter", required = false) String filter) {
        if ("appuser-is-null".equals(filter)) {
            log.debug("REST request to get all Tutors where appUser is null");
            return tutorService.findAllWhereAppUserIsNull();
        }
        log.debug("REST request to get all Tutors");
        return tutorService.findAll();
    }

    /**
     * {@code GET  /tutors/:id} : get the "id" tutor.
     *
     * @param id the id of the tutorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> getTutor(@PathVariable("id") Long id) {
        log.debug("REST request to get Tutor : {}", id);
        Optional<TutorDTO> tutorDTO = tutorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tutorDTO);
    }

    /**
     * {@code DELETE  /tutors/:id} : delete the "id" tutor.
     *
     * @param id the id of the tutorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tutor : {}", id);
        tutorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/GetCustom/{id}")
    public ResponseEntity<TuTorCusTomDTO> getTutorCustom(@PathVariable("id") Long id) {
        log.debug("REST request to get Tutor : {}", id);
        Optional<TuTorCusTomDTO> TuTorCusTomDTO = tutorService.findOneCustom(id);
        return ResponseUtil.wrapOrNotFound(TuTorCusTomDTO);
    }

    @GetMapping("/by-subject")
    public List<ListOfTutorDTO> getTutorsBySubject(@RequestParam String subject) {
        return tutorService.getTutorsBySubject(subject);
    }

    @PostMapping("/tutors/{login}/status/online")
    public ResponseEntity<Void> updateTutorStatusOnline(@PathVariable String login) {
        tutorService.updateTutorStatusOnline(login);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tutors/{login}/status/offline")
    public ResponseEntity<Void> updateTutorStatusOffline(@PathVariable String login) {
        tutorService.updateTutorStatusOffline(login);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tutors/{id}/status/Confirming")
    public ResponseEntity<Void> updateTutorStatusConfirming(@PathVariable Long id) {
        tutorService.updateTutorStatusConFirming(id);
        return ResponseEntity.ok().build();
    }
}
