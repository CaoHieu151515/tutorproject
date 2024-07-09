package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.TutorDetailsAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.repository.TutorDetailsRepository;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.mapper.TutorDetailsMapper;
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
 * Integration tests for the {@link TutorDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TutorDetailsResourceIT {

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tutor-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TutorDetailsRepository tutorDetailsRepository;

    @Autowired
    private TutorDetailsMapper tutorDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTutorDetailsMockMvc;

    private TutorDetails tutorDetails;

    private TutorDetails insertedTutorDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TutorDetails createEntity(EntityManager em) {
        TutorDetails tutorDetails = new TutorDetails().information(DEFAULT_INFORMATION);
        return tutorDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TutorDetails createUpdatedEntity(EntityManager em) {
        TutorDetails tutorDetails = new TutorDetails().information(UPDATED_INFORMATION);
        return tutorDetails;
    }

    @BeforeEach
    public void initTest() {
        tutorDetails = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTutorDetails != null) {
            tutorDetailsRepository.delete(insertedTutorDetails);
            insertedTutorDetails = null;
        }
    }

    @Test
    @Transactional
    void createTutorDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TutorDetails
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);
        var returnedTutorDetailsDTO = om.readValue(
            restTutorDetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDetailsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TutorDetailsDTO.class
        );

        // Validate the TutorDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTutorDetails = tutorDetailsMapper.toEntity(returnedTutorDetailsDTO);
        assertTutorDetailsUpdatableFieldsEquals(returnedTutorDetails, getPersistedTutorDetails(returnedTutorDetails));

        insertedTutorDetails = returnedTutorDetails;
    }

    @Test
    @Transactional
    void createTutorDetailsWithExistingId() throws Exception {
        // Create the TutorDetails with an existing ID
        tutorDetails.setId(1L);
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTutorDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTutorDetails() throws Exception {
        // Initialize the database
        insertedTutorDetails = tutorDetailsRepository.saveAndFlush(tutorDetails);

        // Get all the tutorDetailsList
        restTutorDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tutorDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION)));
    }

    @Test
    @Transactional
    void getTutorDetails() throws Exception {
        // Initialize the database
        insertedTutorDetails = tutorDetailsRepository.saveAndFlush(tutorDetails);

        // Get the tutorDetails
        restTutorDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, tutorDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tutorDetails.getId().intValue()))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION));
    }

    @Test
    @Transactional
    void getNonExistingTutorDetails() throws Exception {
        // Get the tutorDetails
        restTutorDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTutorDetails() throws Exception {
        // Initialize the database
        insertedTutorDetails = tutorDetailsRepository.saveAndFlush(tutorDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorDetails
        TutorDetails updatedTutorDetails = tutorDetailsRepository.findById(tutorDetails.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTutorDetails are not directly saved in db
        em.detach(updatedTutorDetails);
        updatedTutorDetails.information(UPDATED_INFORMATION);
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(updatedTutorDetails);

        restTutorDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTutorDetailsToMatchAllProperties(updatedTutorDetails);
    }

    @Test
    @Transactional
    void putNonExistingTutorDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorDetails.setId(longCount.incrementAndGet());

        // Create the TutorDetails
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTutorDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorDetails.setId(longCount.incrementAndGet());

        // Create the TutorDetails
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTutorDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorDetails.setId(longCount.incrementAndGet());

        // Create the TutorDetails
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTutorDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedTutorDetails = tutorDetailsRepository.saveAndFlush(tutorDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorDetails using partial update
        TutorDetails partialUpdatedTutorDetails = new TutorDetails();
        partialUpdatedTutorDetails.setId(tutorDetails.getId());

        restTutorDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutorDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTutorDetails))
            )
            .andExpect(status().isOk());

        // Validate the TutorDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTutorDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTutorDetails, tutorDetails),
            getPersistedTutorDetails(tutorDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateTutorDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedTutorDetails = tutorDetailsRepository.saveAndFlush(tutorDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorDetails using partial update
        TutorDetails partialUpdatedTutorDetails = new TutorDetails();
        partialUpdatedTutorDetails.setId(tutorDetails.getId());

        partialUpdatedTutorDetails.information(UPDATED_INFORMATION);

        restTutorDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutorDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTutorDetails))
            )
            .andExpect(status().isOk());

        // Validate the TutorDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTutorDetailsUpdatableFieldsEquals(partialUpdatedTutorDetails, getPersistedTutorDetails(partialUpdatedTutorDetails));
    }

    @Test
    @Transactional
    void patchNonExistingTutorDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorDetails.setId(longCount.incrementAndGet());

        // Create the TutorDetails
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tutorDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tutorDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTutorDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorDetails.setId(longCount.incrementAndGet());

        // Create the TutorDetails
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tutorDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTutorDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorDetails.setId(longCount.incrementAndGet());

        // Create the TutorDetails
        TutorDetailsDTO tutorDetailsDTO = tutorDetailsMapper.toDto(tutorDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorDetailsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tutorDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TutorDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTutorDetails() throws Exception {
        // Initialize the database
        insertedTutorDetails = tutorDetailsRepository.saveAndFlush(tutorDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tutorDetails
        restTutorDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, tutorDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tutorDetailsRepository.count();
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

    protected TutorDetails getPersistedTutorDetails(TutorDetails tutorDetails) {
        return tutorDetailsRepository.findById(tutorDetails.getId()).orElseThrow();
    }

    protected void assertPersistedTutorDetailsToMatchAllProperties(TutorDetails expectedTutorDetails) {
        assertTutorDetailsAllPropertiesEquals(expectedTutorDetails, getPersistedTutorDetails(expectedTutorDetails));
    }

    protected void assertPersistedTutorDetailsToMatchUpdatableProperties(TutorDetails expectedTutorDetails) {
        assertTutorDetailsAllUpdatablePropertiesEquals(expectedTutorDetails, getPersistedTutorDetails(expectedTutorDetails));
    }
}
