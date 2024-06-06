package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.HiringHoursAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.HiringHours;
import com.tutor.auth0r.repository.HiringHoursRepository;
import com.tutor.auth0r.service.dto.HiringHoursDTO;
import com.tutor.auth0r.service.mapper.HiringHoursMapper;
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
 * Integration tests for the {@link HiringHoursResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HiringHoursResourceIT {

    private static final Integer DEFAULT_HOUR = 1;
    private static final Integer UPDATED_HOUR = 2;

    private static final String ENTITY_API_URL = "/api/hiring-hours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HiringHoursRepository hiringHoursRepository;

    @Autowired
    private HiringHoursMapper hiringHoursMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHiringHoursMockMvc;

    private HiringHours hiringHours;

    private HiringHours insertedHiringHours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HiringHours createEntity(EntityManager em) {
        HiringHours hiringHours = new HiringHours().hour(DEFAULT_HOUR);
        return hiringHours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HiringHours createUpdatedEntity(EntityManager em) {
        HiringHours hiringHours = new HiringHours().hour(UPDATED_HOUR);
        return hiringHours;
    }

    @BeforeEach
    public void initTest() {
        hiringHours = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedHiringHours != null) {
            hiringHoursRepository.delete(insertedHiringHours);
            insertedHiringHours = null;
        }
    }

    @Test
    @Transactional
    void createHiringHours() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HiringHours
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);
        var returnedHiringHoursDTO = om.readValue(
            restHiringHoursMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hiringHoursDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HiringHoursDTO.class
        );

        // Validate the HiringHours in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHiringHours = hiringHoursMapper.toEntity(returnedHiringHoursDTO);
        assertHiringHoursUpdatableFieldsEquals(returnedHiringHours, getPersistedHiringHours(returnedHiringHours));

        insertedHiringHours = returnedHiringHours;
    }

    @Test
    @Transactional
    void createHiringHoursWithExistingId() throws Exception {
        // Create the HiringHours with an existing ID
        hiringHours.setId(1L);
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHiringHoursMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hiringHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHiringHours() throws Exception {
        // Initialize the database
        insertedHiringHours = hiringHoursRepository.saveAndFlush(hiringHours);

        // Get all the hiringHoursList
        restHiringHoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hiringHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)));
    }

    @Test
    @Transactional
    void getHiringHours() throws Exception {
        // Initialize the database
        insertedHiringHours = hiringHoursRepository.saveAndFlush(hiringHours);

        // Get the hiringHours
        restHiringHoursMockMvc
            .perform(get(ENTITY_API_URL_ID, hiringHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hiringHours.getId().intValue()))
            .andExpect(jsonPath("$.hour").value(DEFAULT_HOUR));
    }

    @Test
    @Transactional
    void getNonExistingHiringHours() throws Exception {
        // Get the hiringHours
        restHiringHoursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHiringHours() throws Exception {
        // Initialize the database
        insertedHiringHours = hiringHoursRepository.saveAndFlush(hiringHours);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hiringHours
        HiringHours updatedHiringHours = hiringHoursRepository.findById(hiringHours.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHiringHours are not directly saved in db
        em.detach(updatedHiringHours);
        updatedHiringHours.hour(UPDATED_HOUR);
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(updatedHiringHours);

        restHiringHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hiringHoursDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hiringHoursDTO))
            )
            .andExpect(status().isOk());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHiringHoursToMatchAllProperties(updatedHiringHours);
    }

    @Test
    @Transactional
    void putNonExistingHiringHours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hiringHours.setId(longCount.incrementAndGet());

        // Create the HiringHours
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHiringHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hiringHoursDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hiringHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHiringHours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hiringHours.setId(longCount.incrementAndGet());

        // Create the HiringHours
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHiringHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hiringHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHiringHours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hiringHours.setId(longCount.incrementAndGet());

        // Create the HiringHours
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHiringHoursMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hiringHoursDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHiringHoursWithPatch() throws Exception {
        // Initialize the database
        insertedHiringHours = hiringHoursRepository.saveAndFlush(hiringHours);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hiringHours using partial update
        HiringHours partialUpdatedHiringHours = new HiringHours();
        partialUpdatedHiringHours.setId(hiringHours.getId());

        partialUpdatedHiringHours.hour(UPDATED_HOUR);

        restHiringHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHiringHours.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHiringHours))
            )
            .andExpect(status().isOk());

        // Validate the HiringHours in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHiringHoursUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHiringHours, hiringHours),
            getPersistedHiringHours(hiringHours)
        );
    }

    @Test
    @Transactional
    void fullUpdateHiringHoursWithPatch() throws Exception {
        // Initialize the database
        insertedHiringHours = hiringHoursRepository.saveAndFlush(hiringHours);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hiringHours using partial update
        HiringHours partialUpdatedHiringHours = new HiringHours();
        partialUpdatedHiringHours.setId(hiringHours.getId());

        partialUpdatedHiringHours.hour(UPDATED_HOUR);

        restHiringHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHiringHours.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHiringHours))
            )
            .andExpect(status().isOk());

        // Validate the HiringHours in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHiringHoursUpdatableFieldsEquals(partialUpdatedHiringHours, getPersistedHiringHours(partialUpdatedHiringHours));
    }

    @Test
    @Transactional
    void patchNonExistingHiringHours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hiringHours.setId(longCount.incrementAndGet());

        // Create the HiringHours
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHiringHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hiringHoursDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hiringHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHiringHours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hiringHours.setId(longCount.incrementAndGet());

        // Create the HiringHours
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHiringHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hiringHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHiringHours() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hiringHours.setId(longCount.incrementAndGet());

        // Create the HiringHours
        HiringHoursDTO hiringHoursDTO = hiringHoursMapper.toDto(hiringHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHiringHoursMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hiringHoursDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HiringHours in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHiringHours() throws Exception {
        // Initialize the database
        insertedHiringHours = hiringHoursRepository.saveAndFlush(hiringHours);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hiringHours
        restHiringHoursMockMvc
            .perform(delete(ENTITY_API_URL_ID, hiringHours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hiringHoursRepository.count();
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

    protected HiringHours getPersistedHiringHours(HiringHours hiringHours) {
        return hiringHoursRepository.findById(hiringHours.getId()).orElseThrow();
    }

    protected void assertPersistedHiringHoursToMatchAllProperties(HiringHours expectedHiringHours) {
        assertHiringHoursAllPropertiesEquals(expectedHiringHours, getPersistedHiringHours(expectedHiringHours));
    }

    protected void assertPersistedHiringHoursToMatchUpdatableProperties(HiringHours expectedHiringHours) {
        assertHiringHoursAllUpdatablePropertiesEquals(expectedHiringHours, getPersistedHiringHours(expectedHiringHours));
    }
}
