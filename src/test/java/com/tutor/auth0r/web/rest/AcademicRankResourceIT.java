package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.AcademicRankAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.AcademicRank;
import com.tutor.auth0r.domain.enumeration.Rank;
import com.tutor.auth0r.repository.AcademicRankRepository;
import com.tutor.auth0r.service.dto.AcademicRankDTO;
import com.tutor.auth0r.service.mapper.AcademicRankMapper;
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
 * Integration tests for the {@link AcademicRankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcademicRankResourceIT {

    private static final Rank DEFAULT_TYPE = Rank.BACHELOR;
    private static final Rank UPDATED_TYPE = Rank.MASTER;

    private static final String ENTITY_API_URL = "/api/academic-ranks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AcademicRankRepository academicRankRepository;

    @Autowired
    private AcademicRankMapper academicRankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicRankMockMvc;

    private AcademicRank academicRank;

    private AcademicRank insertedAcademicRank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicRank createEntity(EntityManager em) {
        AcademicRank academicRank = new AcademicRank().type(DEFAULT_TYPE);
        return academicRank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicRank createUpdatedEntity(EntityManager em) {
        AcademicRank academicRank = new AcademicRank().type(UPDATED_TYPE);
        return academicRank;
    }

    @BeforeEach
    public void initTest() {
        academicRank = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAcademicRank != null) {
            academicRankRepository.delete(insertedAcademicRank);
            insertedAcademicRank = null;
        }
    }

    @Test
    @Transactional
    void createAcademicRank() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AcademicRank
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);
        var returnedAcademicRankDTO = om.readValue(
            restAcademicRankMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(academicRankDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AcademicRankDTO.class
        );

        // Validate the AcademicRank in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAcademicRank = academicRankMapper.toEntity(returnedAcademicRankDTO);
        assertAcademicRankUpdatableFieldsEquals(returnedAcademicRank, getPersistedAcademicRank(returnedAcademicRank));

        insertedAcademicRank = returnedAcademicRank;
    }

    @Test
    @Transactional
    void createAcademicRankWithExistingId() throws Exception {
        // Create the AcademicRank with an existing ID
        academicRank.setId(1L);
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicRankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(academicRankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcademicRanks() throws Exception {
        // Initialize the database
        insertedAcademicRank = academicRankRepository.saveAndFlush(academicRank);

        // Get all the academicRankList
        restAcademicRankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicRank.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getAcademicRank() throws Exception {
        // Initialize the database
        insertedAcademicRank = academicRankRepository.saveAndFlush(academicRank);

        // Get the academicRank
        restAcademicRankMockMvc
            .perform(get(ENTITY_API_URL_ID, academicRank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicRank.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAcademicRank() throws Exception {
        // Get the academicRank
        restAcademicRankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcademicRank() throws Exception {
        // Initialize the database
        insertedAcademicRank = academicRankRepository.saveAndFlush(academicRank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the academicRank
        AcademicRank updatedAcademicRank = academicRankRepository.findById(academicRank.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAcademicRank are not directly saved in db
        em.detach(updatedAcademicRank);
        updatedAcademicRank.type(UPDATED_TYPE);
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(updatedAcademicRank);

        restAcademicRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicRankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(academicRankDTO))
            )
            .andExpect(status().isOk());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAcademicRankToMatchAllProperties(updatedAcademicRank);
    }

    @Test
    @Transactional
    void putNonExistingAcademicRank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academicRank.setId(longCount.incrementAndGet());

        // Create the AcademicRank
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicRankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(academicRankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcademicRank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academicRank.setId(longCount.incrementAndGet());

        // Create the AcademicRank
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicRankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(academicRankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcademicRank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academicRank.setId(longCount.incrementAndGet());

        // Create the AcademicRank
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicRankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(academicRankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcademicRankWithPatch() throws Exception {
        // Initialize the database
        insertedAcademicRank = academicRankRepository.saveAndFlush(academicRank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the academicRank using partial update
        AcademicRank partialUpdatedAcademicRank = new AcademicRank();
        partialUpdatedAcademicRank.setId(academicRank.getId());

        partialUpdatedAcademicRank.type(UPDATED_TYPE);

        restAcademicRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicRank.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAcademicRank))
            )
            .andExpect(status().isOk());

        // Validate the AcademicRank in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAcademicRankUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAcademicRank, academicRank),
            getPersistedAcademicRank(academicRank)
        );
    }

    @Test
    @Transactional
    void fullUpdateAcademicRankWithPatch() throws Exception {
        // Initialize the database
        insertedAcademicRank = academicRankRepository.saveAndFlush(academicRank);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the academicRank using partial update
        AcademicRank partialUpdatedAcademicRank = new AcademicRank();
        partialUpdatedAcademicRank.setId(academicRank.getId());

        partialUpdatedAcademicRank.type(UPDATED_TYPE);

        restAcademicRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicRank.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAcademicRank))
            )
            .andExpect(status().isOk());

        // Validate the AcademicRank in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAcademicRankUpdatableFieldsEquals(partialUpdatedAcademicRank, getPersistedAcademicRank(partialUpdatedAcademicRank));
    }

    @Test
    @Transactional
    void patchNonExistingAcademicRank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academicRank.setId(longCount.incrementAndGet());

        // Create the AcademicRank
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, academicRankDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(academicRankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcademicRank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academicRank.setId(longCount.incrementAndGet());

        // Create the AcademicRank
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicRankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(academicRankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcademicRank() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        academicRank.setId(longCount.incrementAndGet());

        // Create the AcademicRank
        AcademicRankDTO academicRankDTO = academicRankMapper.toDto(academicRank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicRankMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(academicRankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicRank in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcademicRank() throws Exception {
        // Initialize the database
        insertedAcademicRank = academicRankRepository.saveAndFlush(academicRank);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the academicRank
        restAcademicRankMockMvc
            .perform(delete(ENTITY_API_URL_ID, academicRank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return academicRankRepository.count();
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

    protected AcademicRank getPersistedAcademicRank(AcademicRank academicRank) {
        return academicRankRepository.findById(academicRank.getId()).orElseThrow();
    }

    protected void assertPersistedAcademicRankToMatchAllProperties(AcademicRank expectedAcademicRank) {
        assertAcademicRankAllPropertiesEquals(expectedAcademicRank, getPersistedAcademicRank(expectedAcademicRank));
    }

    protected void assertPersistedAcademicRankToMatchUpdatableProperties(AcademicRank expectedAcademicRank) {
        assertAcademicRankAllUpdatablePropertiesEquals(expectedAcademicRank, getPersistedAcademicRank(expectedAcademicRank));
    }
}
