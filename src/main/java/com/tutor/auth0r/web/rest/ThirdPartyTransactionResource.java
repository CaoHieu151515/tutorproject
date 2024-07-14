package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.repository.ThirdPartyTransactionRepository;
import com.tutor.auth0r.service.ThirdPartyTransactionService;
import com.tutor.auth0r.service.dto.ThirdPartyTransactionDTO;
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
 * REST controller for managing {@link com.tutor.auth0r.domain.ThirdPartyTransaction}.
 */
@RestController
@RequestMapping("/api/third-party-transactions")
public class ThirdPartyTransactionResource {

    private static final Logger log = LoggerFactory.getLogger(ThirdPartyTransactionResource.class);

    private static final String ENTITY_NAME = "thirdPartyTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThirdPartyTransactionService thirdPartyTransactionService;

    private final ThirdPartyTransactionRepository thirdPartyTransactionRepository;

    public ThirdPartyTransactionResource(
        ThirdPartyTransactionService thirdPartyTransactionService,
        ThirdPartyTransactionRepository thirdPartyTransactionRepository
    ) {
        this.thirdPartyTransactionService = thirdPartyTransactionService;
        this.thirdPartyTransactionRepository = thirdPartyTransactionRepository;
    }

    /**
     * {@code POST  /third-party-transactions} : Create a new thirdPartyTransaction.
     *
     * @param thirdPartyTransactionDTO the thirdPartyTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thirdPartyTransactionDTO, or with status {@code 400 (Bad Request)} if the thirdPartyTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ThirdPartyTransactionDTO> createThirdPartyTransaction(
        @Valid @RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ThirdPartyTransaction : {}", thirdPartyTransactionDTO);
        if (thirdPartyTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new thirdPartyTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thirdPartyTransactionDTO = thirdPartyTransactionService.save(thirdPartyTransactionDTO);
        return ResponseEntity.created(new URI("/api/third-party-transactions/" + thirdPartyTransactionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, thirdPartyTransactionDTO.getId().toString()))
            .body(thirdPartyTransactionDTO);
    }

    /**
     * {@code PUT  /third-party-transactions/:id} : Updates an existing thirdPartyTransaction.
     *
     * @param id the id of the thirdPartyTransactionDTO to save.
     * @param thirdPartyTransactionDTO the thirdPartyTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thirdPartyTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the thirdPartyTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thirdPartyTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ThirdPartyTransactionDTO> updateThirdPartyTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ThirdPartyTransaction : {}, {}", id, thirdPartyTransactionDTO);
        if (thirdPartyTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thirdPartyTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thirdPartyTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thirdPartyTransactionDTO = thirdPartyTransactionService.update(thirdPartyTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thirdPartyTransactionDTO.getId().toString()))
            .body(thirdPartyTransactionDTO);
    }

    /**
     * {@code PATCH  /third-party-transactions/:id} : Partial updates given fields of an existing thirdPartyTransaction, field will ignore if it is null
     *
     * @param id the id of the thirdPartyTransactionDTO to save.
     * @param thirdPartyTransactionDTO the thirdPartyTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thirdPartyTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the thirdPartyTransactionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thirdPartyTransactionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thirdPartyTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThirdPartyTransactionDTO> partialUpdateThirdPartyTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThirdPartyTransaction partially : {}, {}", id, thirdPartyTransactionDTO);
        if (thirdPartyTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thirdPartyTransactionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thirdPartyTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThirdPartyTransactionDTO> result = thirdPartyTransactionService.partialUpdate(thirdPartyTransactionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thirdPartyTransactionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /third-party-transactions} : get all the thirdPartyTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thirdPartyTransactions in body.
     */
    @GetMapping("")
    public List<ThirdPartyTransactionDTO> getAllThirdPartyTransactions() {
        log.debug("REST request to get all ThirdPartyTransactions");
        return thirdPartyTransactionService.findAll();
    }

    /**
     * {@code GET  /third-party-transactions/:id} : get the "id" thirdPartyTransaction.
     *
     * @param id the id of the thirdPartyTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thirdPartyTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ThirdPartyTransactionDTO> getThirdPartyTransaction(@PathVariable("id") Long id) {
        log.debug("REST request to get ThirdPartyTransaction : {}", id);
        Optional<ThirdPartyTransactionDTO> thirdPartyTransactionDTO = thirdPartyTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thirdPartyTransactionDTO);
    }

    /**
     * {@code DELETE  /third-party-transactions/:id} : delete the "id" thirdPartyTransaction.
     *
     * @param id the id of the thirdPartyTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThirdPartyTransaction(@PathVariable("id") Long id) {
        log.debug("REST request to delete ThirdPartyTransaction : {}", id);
        thirdPartyTransactionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
