package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.TuTorContactWithAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.TuTorContactWith;
import com.tutor.auth0r.domain.enumeration.Contact;
import com.tutor.auth0r.repository.TuTorContactWithRepository;
import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import com.tutor.auth0r.service.mapper.TuTorContactWithMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TuTorContactWithResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TuTorContactWithResourceIT {

    private static final String DEFAULT_URL_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_URL_CONTACT = "BBBBBBBBBB";

    private static final Contact DEFAULT_TYPE = Contact.MEET;
    private static final Contact UPDATED_TYPE = Contact.DISCORD;

    private static final String ENTITY_API_URL = "/api/tu-tor-contact-withs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TuTorContactWithRepository tuTorContactWithRepository;

    @Autowired
    private TuTorContactWithMapper tuTorContactWithMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTuTorContactWithMockMvc;

    private TuTorContactWith tuTorContactWith;

    private TuTorContactWith insertedTuTorContactWith;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TuTorContactWith createEntity(EntityManager em) {
        TuTorContactWith tuTorContactWith = new TuTorContactWith().urlContact(DEFAULT_URL_CONTACT).type(DEFAULT_TYPE);
        return tuTorContactWith;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TuTorContactWith createUpdatedEntity(EntityManager em) {
        TuTorContactWith tuTorContactWith = new TuTorContactWith().urlContact(UPDATED_URL_CONTACT).type(UPDATED_TYPE);
        return tuTorContactWith;
    }

    @BeforeEach
    public void initTest() {
        tuTorContactWith = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTuTorContactWith != null) {
            tuTorContactWithRepository.delete(insertedTuTorContactWith);
            insertedTuTorContactWith = null;
        }
    }

    @Test
    @Transactional
    void createTuTorContactWith() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TuTorContactWith
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);
        var returnedTuTorContactWithDTO = om.readValue(
            restTuTorContactWithMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tuTorContactWithDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TuTorContactWithDTO.class
        );

        // Validate the TuTorContactWith in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTuTorContactWith = tuTorContactWithMapper.toEntity(returnedTuTorContactWithDTO);
        assertTuTorContactWithUpdatableFieldsEquals(returnedTuTorContactWith, getPersistedTuTorContactWith(returnedTuTorContactWith));

        insertedTuTorContactWith = returnedTuTorContactWith;
    }

    @Test
    @Transactional
    void createTuTorContactWithWithExistingId() throws Exception {
        // Create the TuTorContactWith with an existing ID
        tuTorContactWith.setId(1L);
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTuTorContactWithMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tuTorContactWithDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTuTorContactWiths() throws Exception {
        // Initialize the database
        insertedTuTorContactWith = tuTorContactWithRepository.saveAndFlush(tuTorContactWith);

        // Get all the tuTorContactWithList
        restTuTorContactWithMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tuTorContactWith.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlContact").value(hasItem(DEFAULT_URL_CONTACT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getTuTorContactWith() throws Exception {
        // Initialize the database
        insertedTuTorContactWith = tuTorContactWithRepository.saveAndFlush(tuTorContactWith);

        // Get the tuTorContactWith
        restTuTorContactWithMockMvc
            .perform(get(ENTITY_API_URL_ID, tuTorContactWith.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tuTorContactWith.getId().intValue()))
            .andExpect(jsonPath("$.urlContact").value(DEFAULT_URL_CONTACT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTuTorContactWith() throws Exception {
        // Get the tuTorContactWith
        restTuTorContactWithMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTuTorContactWith() throws Exception {
        // Initialize the database
        insertedTuTorContactWith = tuTorContactWithRepository.saveAndFlush(tuTorContactWith);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tuTorContactWith
        TuTorContactWith updatedTuTorContactWith = tuTorContactWithRepository.findById(tuTorContactWith.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTuTorContactWith are not directly saved in db
        em.detach(updatedTuTorContactWith);
        updatedTuTorContactWith.urlContact(UPDATED_URL_CONTACT).type(UPDATED_TYPE);
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(updatedTuTorContactWith);

        restTuTorContactWithMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tuTorContactWithDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tuTorContactWithDTO))
            )
            .andExpect(status().isOk());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTuTorContactWithToMatchAllProperties(updatedTuTorContactWith);
    }

    @Test
    @Transactional
    void putNonExistingTuTorContactWith() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tuTorContactWith.setId(longCount.incrementAndGet());

        // Create the TuTorContactWith
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTuTorContactWithMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tuTorContactWithDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tuTorContactWithDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTuTorContactWith() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tuTorContactWith.setId(longCount.incrementAndGet());

        // Create the TuTorContactWith
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTuTorContactWithMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tuTorContactWithDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTuTorContactWith() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tuTorContactWith.setId(longCount.incrementAndGet());

        // Create the TuTorContactWith
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTuTorContactWithMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tuTorContactWithDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTuTorContactWithWithPatch() throws Exception {
        // Initialize the database
        insertedTuTorContactWith = tuTorContactWithRepository.saveAndFlush(tuTorContactWith);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tuTorContactWith using partial update
        TuTorContactWith partialUpdatedTuTorContactWith = new TuTorContactWith();
        partialUpdatedTuTorContactWith.setId(tuTorContactWith.getId());

        partialUpdatedTuTorContactWith.type(UPDATED_TYPE);

        restTuTorContactWithMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTuTorContactWith.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTuTorContactWith))
            )
            .andExpect(status().isOk());

        // Validate the TuTorContactWith in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTuTorContactWithUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTuTorContactWith, tuTorContactWith),
            getPersistedTuTorContactWith(tuTorContactWith)
        );
    }

    @Test
    @Transactional
    void fullUpdateTuTorContactWithWithPatch() throws Exception {
        // Initialize the database
        insertedTuTorContactWith = tuTorContactWithRepository.saveAndFlush(tuTorContactWith);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tuTorContactWith using partial update
        TuTorContactWith partialUpdatedTuTorContactWith = new TuTorContactWith();
        partialUpdatedTuTorContactWith.setId(tuTorContactWith.getId());

        partialUpdatedTuTorContactWith.urlContact(UPDATED_URL_CONTACT).type(UPDATED_TYPE);

        restTuTorContactWithMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTuTorContactWith.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTuTorContactWith))
            )
            .andExpect(status().isOk());

        // Validate the TuTorContactWith in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTuTorContactWithUpdatableFieldsEquals(
            partialUpdatedTuTorContactWith,
            getPersistedTuTorContactWith(partialUpdatedTuTorContactWith)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTuTorContactWith() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tuTorContactWith.setId(longCount.incrementAndGet());

        // Create the TuTorContactWith
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTuTorContactWithMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tuTorContactWithDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tuTorContactWithDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTuTorContactWith() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tuTorContactWith.setId(longCount.incrementAndGet());

        // Create the TuTorContactWith
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTuTorContactWithMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tuTorContactWithDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTuTorContactWith() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tuTorContactWith.setId(longCount.incrementAndGet());

        // Create the TuTorContactWith
        TuTorContactWithDTO tuTorContactWithDTO = tuTorContactWithMapper.toDto(tuTorContactWith);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTuTorContactWithMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tuTorContactWithDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TuTorContactWith in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTuTorContactWith() throws Exception {
        // Initialize the database
        insertedTuTorContactWith = tuTorContactWithRepository.saveAndFlush(tuTorContactWith);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tuTorContactWith
        restTuTorContactWithMockMvc
            .perform(delete(ENTITY_API_URL_ID, tuTorContactWith.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tuTorContactWithRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected TuTorContactWith getPersistedTuTorContactWith(TuTorContactWith tuTorContactWith) {
        return tuTorContactWithRepository.findById(tuTorContactWith.getId()).orElseThrow();
    }

    protected void assertPersistedTuTorContactWithToMatchAllProperties(TuTorContactWith expectedTuTorContactWith) {
        assertTuTorContactWithAllPropertiesEquals(expectedTuTorContactWith, getPersistedTuTorContactWith(expectedTuTorContactWith));
    }

    protected void assertPersistedTuTorContactWithToMatchUpdatableProperties(TuTorContactWith expectedTuTorContactWith) {
        assertTuTorContactWithAllUpdatablePropertiesEquals(
            expectedTuTorContactWith,
            getPersistedTuTorContactWith(expectedTuTorContactWith)
        );
    }
}
