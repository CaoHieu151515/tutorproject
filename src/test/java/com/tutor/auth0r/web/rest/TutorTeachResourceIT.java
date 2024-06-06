package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.TutorTeachAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.domain.enumeration.Teach;
import com.tutor.auth0r.repository.TutorTeachRepository;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import com.tutor.auth0r.service.mapper.TutorTeachMapper;
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
 * Integration tests for the {@link TutorTeachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TutorTeachResourceIT {

    private static final Teach DEFAULT_SUBJECT = Teach.MATH_10;
    private static final Teach UPDATED_SUBJECT = Teach.MATH_11;

    private static final String ENTITY_API_URL = "/api/tutor-teaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TutorTeachRepository tutorTeachRepository;

    @Autowired
    private TutorTeachMapper tutorTeachMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTutorTeachMockMvc;

    private TutorTeach tutorTeach;

    private TutorTeach insertedTutorTeach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TutorTeach createEntity(EntityManager em) {
        TutorTeach tutorTeach = new TutorTeach().subject(DEFAULT_SUBJECT);
        return tutorTeach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TutorTeach createUpdatedEntity(EntityManager em) {
        TutorTeach tutorTeach = new TutorTeach().subject(UPDATED_SUBJECT);
        return tutorTeach;
    }

    @BeforeEach
    public void initTest() {
        tutorTeach = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTutorTeach != null) {
            tutorTeachRepository.delete(insertedTutorTeach);
            insertedTutorTeach = null;
        }
    }

    @Test
    @Transactional
    void createTutorTeach() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TutorTeach
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);
        var returnedTutorTeachDTO = om.readValue(
            restTutorTeachMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorTeachDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TutorTeachDTO.class
        );

        // Validate the TutorTeach in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTutorTeach = tutorTeachMapper.toEntity(returnedTutorTeachDTO);
        assertTutorTeachUpdatableFieldsEquals(returnedTutorTeach, getPersistedTutorTeach(returnedTutorTeach));

        insertedTutorTeach = returnedTutorTeach;
    }

    @Test
    @Transactional
    void createTutorTeachWithExistingId() throws Exception {
        // Create the TutorTeach with an existing ID
        tutorTeach.setId(1L);
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTutorTeachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorTeachDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTutorTeaches() throws Exception {
        // Initialize the database
        insertedTutorTeach = tutorTeachRepository.saveAndFlush(tutorTeach);

        // Get all the tutorTeachList
        restTutorTeachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tutorTeach.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())));
    }

    @Test
    @Transactional
    void getTutorTeach() throws Exception {
        // Initialize the database
        insertedTutorTeach = tutorTeachRepository.saveAndFlush(tutorTeach);

        // Get the tutorTeach
        restTutorTeachMockMvc
            .perform(get(ENTITY_API_URL_ID, tutorTeach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tutorTeach.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTutorTeach() throws Exception {
        // Get the tutorTeach
        restTutorTeachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTutorTeach() throws Exception {
        // Initialize the database
        insertedTutorTeach = tutorTeachRepository.saveAndFlush(tutorTeach);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorTeach
        TutorTeach updatedTutorTeach = tutorTeachRepository.findById(tutorTeach.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTutorTeach are not directly saved in db
        em.detach(updatedTutorTeach);
        updatedTutorTeach.subject(UPDATED_SUBJECT);
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(updatedTutorTeach);

        restTutorTeachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorTeachDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorTeachDTO))
            )
            .andExpect(status().isOk());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTutorTeachToMatchAllProperties(updatedTutorTeach);
    }

    @Test
    @Transactional
    void putNonExistingTutorTeach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorTeach.setId(longCount.incrementAndGet());

        // Create the TutorTeach
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorTeachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorTeachDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorTeachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTutorTeach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorTeach.setId(longCount.incrementAndGet());

        // Create the TutorTeach
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorTeachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorTeachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTutorTeach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorTeach.setId(longCount.incrementAndGet());

        // Create the TutorTeach
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorTeachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorTeachDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTutorTeachWithPatch() throws Exception {
        // Initialize the database
        insertedTutorTeach = tutorTeachRepository.saveAndFlush(tutorTeach);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorTeach using partial update
        TutorTeach partialUpdatedTutorTeach = new TutorTeach();
        partialUpdatedTutorTeach.setId(tutorTeach.getId());

        restTutorTeachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutorTeach.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTutorTeach))
            )
            .andExpect(status().isOk());

        // Validate the TutorTeach in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTutorTeachUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTutorTeach, tutorTeach),
            getPersistedTutorTeach(tutorTeach)
        );
    }

    @Test
    @Transactional
    void fullUpdateTutorTeachWithPatch() throws Exception {
        // Initialize the database
        insertedTutorTeach = tutorTeachRepository.saveAndFlush(tutorTeach);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorTeach using partial update
        TutorTeach partialUpdatedTutorTeach = new TutorTeach();
        partialUpdatedTutorTeach.setId(tutorTeach.getId());

        partialUpdatedTutorTeach.subject(UPDATED_SUBJECT);

        restTutorTeachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutorTeach.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTutorTeach))
            )
            .andExpect(status().isOk());

        // Validate the TutorTeach in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTutorTeachUpdatableFieldsEquals(partialUpdatedTutorTeach, getPersistedTutorTeach(partialUpdatedTutorTeach));
    }

    @Test
    @Transactional
    void patchNonExistingTutorTeach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorTeach.setId(longCount.incrementAndGet());

        // Create the TutorTeach
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorTeachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tutorTeachDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tutorTeachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTutorTeach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorTeach.setId(longCount.incrementAndGet());

        // Create the TutorTeach
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorTeachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tutorTeachDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTutorTeach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorTeach.setId(longCount.incrementAndGet());

        // Create the TutorTeach
        TutorTeachDTO tutorTeachDTO = tutorTeachMapper.toDto(tutorTeach);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorTeachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tutorTeachDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TutorTeach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTutorTeach() throws Exception {
        // Initialize the database
        insertedTutorTeach = tutorTeachRepository.saveAndFlush(tutorTeach);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tutorTeach
        restTutorTeachMockMvc
            .perform(delete(ENTITY_API_URL_ID, tutorTeach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tutorTeachRepository.count();
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

    protected TutorTeach getPersistedTutorTeach(TutorTeach tutorTeach) {
        return tutorTeachRepository.findById(tutorTeach.getId()).orElseThrow();
    }

    protected void assertPersistedTutorTeachToMatchAllProperties(TutorTeach expectedTutorTeach) {
        assertTutorTeachAllPropertiesEquals(expectedTutorTeach, getPersistedTutorTeach(expectedTutorTeach));
    }

    protected void assertPersistedTutorTeachToMatchUpdatableProperties(TutorTeach expectedTutorTeach) {
        assertTutorTeachAllUpdatablePropertiesEquals(expectedTutorTeach, getPersistedTutorTeach(expectedTutorTeach));
    }
}
