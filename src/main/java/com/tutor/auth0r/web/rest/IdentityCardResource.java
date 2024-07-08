package com.tutor.auth0r.web.rest;

import com.tutor.auth0r.service.IdentityCardService;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tutor.auth0r.domain.IdentityCard}.
 */
@RestController
@RequestMapping("/api/identity-cards")
public class IdentityCardResource {

    private static final Logger log = LoggerFactory.getLogger(IdentityCardResource.class);

    private static final String ENTITY_NAME = "identityCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdentityCardService identityCardService;

    public IdentityCardResource(IdentityCardService identityCardService) {
        this.identityCardService = identityCardService;
    }

    /**
     * {@code POST  /identity-cards} : Create a new identityCard.
     *
     * @param identityCardDTO the identityCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new identityCardDTO, or with status {@code 400 (Bad Request)} if the identityCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IdentityCardDTO> createIdentityCard(@RequestBody IdentityCardDTO identityCardDTO) throws URISyntaxException {
        log.debug("REST request to save IdentityCard : {}", identityCardDTO);
        if (identityCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new identityCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        identityCardDTO = identityCardService.save(identityCardDTO);
        return ResponseEntity.created(new URI("/api/identity-cards/" + identityCardDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, identityCardDTO.getId().toString()))
            .body(identityCardDTO);
    }

    /**
     * {@code GET  /identity-cards} : get all the identityCards.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of identityCards in body.
     */
    @GetMapping("")
    public List<IdentityCardDTO> getAllIdentityCards(@RequestParam(name = "filter", required = false) String filter) {
        if ("userverify-is-null".equals(filter)) {
            log.debug("REST request to get all IdentityCards where userVerify is null");
            return identityCardService.findAllWhereUserVerifyIsNull();
        }
        log.debug("REST request to get all IdentityCards");
        return identityCardService.findAll();
    }

    /**
     * {@code GET  /identity-cards/:id} : get the "id" identityCard.
     *
     * @param id the id of the identityCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the identityCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IdentityCardDTO> getIdentityCard(@PathVariable("id") Long id) {
        log.debug("REST request to get IdentityCard : {}", id);
        Optional<IdentityCardDTO> identityCardDTO = identityCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(identityCardDTO);
    }

    /**
     * {@code DELETE  /identity-cards/:id} : delete the "id" identityCard.
     *
     * @param id the id of the identityCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdentityCard(@PathVariable("id") Long id) {
        log.debug("REST request to delete IdentityCard : {}", id);
        identityCardService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
