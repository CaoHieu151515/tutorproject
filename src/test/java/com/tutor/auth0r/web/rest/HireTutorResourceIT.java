package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.HireTutorAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.HireTutor;
import com.tutor.auth0r.domain.enumeration.HireStatus;
import com.tutor.auth0r.repository.HireTutorRepository;
import com.tutor.auth0r.service.dto.HireTutorDTO;
import com.tutor.auth0r.service.mapper.HireTutorMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HireTutorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HireTutorResourceIT {

    private static final Integer DEFAULT_TIME_HIRE = 1;
    private static final Integer UPDATED_TIME_HIRE = 2;

    private static final Double DEFAULT_TOTAL_PRICE = 1D;
    private static final Double UPDATED_TOTAL_PRICE = 2D;

    private static final HireStatus DEFAULT_STATUS = HireStatus.DURING;
    private static final HireStatus UPDATED_STATUS = HireStatus.DONE;

    private static final String ENTITY_API_URL = "/api/hire-tutors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HireTutorRepository hireTutorRepository;

    @Autowired
    private HireTutorMapper hireTutorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHireTutorMockMvc;

    private HireTutor hireTutor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HireTutor createEntity(EntityManager em) {
        HireTutor hireTutor = new HireTutor().timeHire(DEFAULT_TIME_HIRE).totalPrice(DEFAULT_TOTAL_PRICE).status(DEFAULT_STATUS);
        return hireTutor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HireTutor createUpdatedEntity(EntityManager em) {
        HireTutor hireTutor = new HireTutor().timeHire(UPDATED_TIME_HIRE).totalPrice(UPDATED_TOTAL_PRICE).status(UPDATED_STATUS);
        return hireTutor;
    }

    @BeforeEach
    public void initTest() {
        hireTutor = createEntity(em);
    }

    @Test
    @Transactional
    void createHireTutor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HireTutor
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);
        var returnedHireTutorDTO = om.readValue(
            restHireTutorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hireTutorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HireTutorDTO.class
        );

        // Validate the HireTutor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHireTutor = hireTutorMapper.toEntity(returnedHireTutorDTO);
        assertHireTutorUpdatableFieldsEquals(returnedHireTutor, getPersistedHireTutor(returnedHireTutor));
    }

    @Test
    @Transactional
    void createHireTutorWithExistingId() throws Exception {
        // Create the HireTutor with an existing ID
        hireTutor.setId(1L);
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHireTutorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hireTutorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHireTutors() throws Exception {
        // Initialize the database
        hireTutorRepository.saveAndFlush(hireTutor);

        // Get all the hireTutorList
        restHireTutorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hireTutor.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeHire").value(hasItem(DEFAULT_TIME_HIRE)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getHireTutor() throws Exception {
        // Initialize the database
        hireTutorRepository.saveAndFlush(hireTutor);

        // Get the hireTutor
        restHireTutorMockMvc
            .perform(get(ENTITY_API_URL_ID, hireTutor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hireTutor.getId().intValue()))
            .andExpect(jsonPath("$.timeHire").value(DEFAULT_TIME_HIRE))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHireTutor() throws Exception {
        // Get the hireTutor
        restHireTutorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHireTutor() throws Exception {
        // Initialize the database
        hireTutorRepository.saveAndFlush(hireTutor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hireTutor
        HireTutor updatedHireTutor = hireTutorRepository.findById(hireTutor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHireTutor are not directly saved in db
        em.detach(updatedHireTutor);
        updatedHireTutor.timeHire(UPDATED_TIME_HIRE).totalPrice(UPDATED_TOTAL_PRICE).status(UPDATED_STATUS);
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(updatedHireTutor);

        restHireTutorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hireTutorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hireTutorDTO))
            )
            .andExpect(status().isOk());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHireTutorToMatchAllProperties(updatedHireTutor);
    }

    @Test
    @Transactional
    void putNonExistingHireTutor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hireTutor.setId(longCount.incrementAndGet());

        // Create the HireTutor
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHireTutorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hireTutorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hireTutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHireTutor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hireTutor.setId(longCount.incrementAndGet());

        // Create the HireTutor
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireTutorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hireTutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHireTutor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hireTutor.setId(longCount.incrementAndGet());

        // Create the HireTutor
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireTutorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hireTutorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHireTutorWithPatch() throws Exception {
        // Initialize the database
        hireTutorRepository.saveAndFlush(hireTutor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hireTutor using partial update
        HireTutor partialUpdatedHireTutor = new HireTutor();
        partialUpdatedHireTutor.setId(hireTutor.getId());

        partialUpdatedHireTutor.status(UPDATED_STATUS);

        restHireTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHireTutor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHireTutor))
            )
            .andExpect(status().isOk());

        // Validate the HireTutor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHireTutorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHireTutor, hireTutor),
            getPersistedHireTutor(hireTutor)
        );
    }

    @Test
    @Transactional
    void fullUpdateHireTutorWithPatch() throws Exception {
        // Initialize the database
        hireTutorRepository.saveAndFlush(hireTutor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hireTutor using partial update
        HireTutor partialUpdatedHireTutor = new HireTutor();
        partialUpdatedHireTutor.setId(hireTutor.getId());

        partialUpdatedHireTutor.timeHire(UPDATED_TIME_HIRE).totalPrice(UPDATED_TOTAL_PRICE).status(UPDATED_STATUS);

        restHireTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHireTutor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHireTutor))
            )
            .andExpect(status().isOk());

        // Validate the HireTutor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHireTutorUpdatableFieldsEquals(partialUpdatedHireTutor, getPersistedHireTutor(partialUpdatedHireTutor));
    }

    @Test
    @Transactional
    void patchNonExistingHireTutor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hireTutor.setId(longCount.incrementAndGet());

        // Create the HireTutor
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHireTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hireTutorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hireTutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHireTutor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hireTutor.setId(longCount.incrementAndGet());

        // Create the HireTutor
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hireTutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHireTutor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hireTutor.setId(longCount.incrementAndGet());

        // Create the HireTutor
        HireTutorDTO hireTutorDTO = hireTutorMapper.toDto(hireTutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireTutorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hireTutorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HireTutor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHireTutor() throws Exception {
        // Initialize the database
        hireTutorRepository.saveAndFlush(hireTutor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hireTutor
        restHireTutorMockMvc
            .perform(delete(ENTITY_API_URL_ID, hireTutor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hireTutorRepository.count();
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

    protected HireTutor getPersistedHireTutor(HireTutor hireTutor) {
        return hireTutorRepository.findById(hireTutor.getId()).orElseThrow();
    }

    protected void assertPersistedHireTutorToMatchAllProperties(HireTutor expectedHireTutor) {
        assertHireTutorAllPropertiesEquals(expectedHireTutor, getPersistedHireTutor(expectedHireTutor));
    }

    protected void assertPersistedHireTutorToMatchUpdatableProperties(HireTutor expectedHireTutor) {
        assertHireTutorAllUpdatablePropertiesEquals(expectedHireTutor, getPersistedHireTutor(expectedHireTutor));
    }
}
