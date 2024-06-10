package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.UserVerifyAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.repository.UserVerifyRepository;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import com.tutor.auth0r.service.mapper.UserVerifyMapper;
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
 * Integration tests for the {@link UserVerifyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserVerifyResourceIT {

    private static final Long DEFAULT_RATING = 1L;
    private static final Long UPDATED_RATING = 2L;

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final Long DEFAULT_GRADUATION_YEAR = 1L;
    private static final Long UPDATED_GRADUATION_YEAR = 2L;

    private static final String ENTITY_API_URL = "/api/user-verifies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserVerifyRepository userVerifyRepository;

    @Autowired
    private UserVerifyMapper userVerifyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserVerifyMockMvc;

    private UserVerify userVerify;

    private UserVerify insertedUserVerify;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVerify createEntity(EntityManager em) {
        UserVerify userVerify = new UserVerify()
            .rating(DEFAULT_RATING)
            .school(DEFAULT_SCHOOL)
            .studentID(DEFAULT_STUDENT_ID)
            .major(DEFAULT_MAJOR)
            .graduationYear(DEFAULT_GRADUATION_YEAR);
        return userVerify;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserVerify createUpdatedEntity(EntityManager em) {
        UserVerify userVerify = new UserVerify()
            .rating(UPDATED_RATING)
            .school(UPDATED_SCHOOL)
            .studentID(UPDATED_STUDENT_ID)
            .major(UPDATED_MAJOR)
            .graduationYear(UPDATED_GRADUATION_YEAR);
        return userVerify;
    }

    @BeforeEach
    public void initTest() {
        userVerify = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedUserVerify != null) {
            userVerifyRepository.delete(insertedUserVerify);
            insertedUserVerify = null;
        }
    }

    @Test
    @Transactional
    void createUserVerify() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UserVerify
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);
        var returnedUserVerifyDTO = om.readValue(
            restUserVerifyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userVerifyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UserVerifyDTO.class
        );

        // Validate the UserVerify in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUserVerify = userVerifyMapper.toEntity(returnedUserVerifyDTO);
        assertUserVerifyUpdatableFieldsEquals(returnedUserVerify, getPersistedUserVerify(returnedUserVerify));

        insertedUserVerify = returnedUserVerify;
    }

    @Test
    @Transactional
    void createUserVerifyWithExistingId() throws Exception {
        // Create the UserVerify with an existing ID
        userVerify.setId(1L);
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserVerifyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userVerifyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserVerifies() throws Exception {
        // Initialize the database
        insertedUserVerify = userVerifyRepository.saveAndFlush(userVerify);

        // Get all the userVerifyList
        restUserVerifyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userVerify.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.intValue())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)))
            .andExpect(jsonPath("$.[*].studentID").value(hasItem(DEFAULT_STUDENT_ID)))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR)))
            .andExpect(jsonPath("$.[*].graduationYear").value(hasItem(DEFAULT_GRADUATION_YEAR.intValue())));
    }

    @Test
    @Transactional
    void getUserVerify() throws Exception {
        // Initialize the database
        insertedUserVerify = userVerifyRepository.saveAndFlush(userVerify);

        // Get the userVerify
        restUserVerifyMockMvc
            .perform(get(ENTITY_API_URL_ID, userVerify.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userVerify.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.intValue()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL))
            .andExpect(jsonPath("$.studentID").value(DEFAULT_STUDENT_ID))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR))
            .andExpect(jsonPath("$.graduationYear").value(DEFAULT_GRADUATION_YEAR.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserVerify() throws Exception {
        // Get the userVerify
        restUserVerifyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserVerify() throws Exception {
        // Initialize the database
        insertedUserVerify = userVerifyRepository.saveAndFlush(userVerify);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userVerify
        UserVerify updatedUserVerify = userVerifyRepository.findById(userVerify.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUserVerify are not directly saved in db
        em.detach(updatedUserVerify);
        updatedUserVerify
            .rating(UPDATED_RATING)
            .school(UPDATED_SCHOOL)
            .studentID(UPDATED_STUDENT_ID)
            .major(UPDATED_MAJOR)
            .graduationYear(UPDATED_GRADUATION_YEAR);
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(updatedUserVerify);

        restUserVerifyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userVerifyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userVerifyDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUserVerifyToMatchAllProperties(updatedUserVerify);
    }

    @Test
    @Transactional
    void putNonExistingUserVerify() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userVerify.setId(longCount.incrementAndGet());

        // Create the UserVerify
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserVerifyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userVerifyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userVerifyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserVerify() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userVerify.setId(longCount.incrementAndGet());

        // Create the UserVerify
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserVerifyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userVerifyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserVerify() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userVerify.setId(longCount.incrementAndGet());

        // Create the UserVerify
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserVerifyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userVerifyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserVerifyWithPatch() throws Exception {
        // Initialize the database
        insertedUserVerify = userVerifyRepository.saveAndFlush(userVerify);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userVerify using partial update
        UserVerify partialUpdatedUserVerify = new UserVerify();
        partialUpdatedUserVerify.setId(userVerify.getId());

        partialUpdatedUserVerify.rating(UPDATED_RATING).school(UPDATED_SCHOOL).major(UPDATED_MAJOR);

        restUserVerifyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserVerify.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserVerify))
            )
            .andExpect(status().isOk());

        // Validate the UserVerify in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserVerifyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUserVerify, userVerify),
            getPersistedUserVerify(userVerify)
        );
    }

    @Test
    @Transactional
    void fullUpdateUserVerifyWithPatch() throws Exception {
        // Initialize the database
        insertedUserVerify = userVerifyRepository.saveAndFlush(userVerify);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userVerify using partial update
        UserVerify partialUpdatedUserVerify = new UserVerify();
        partialUpdatedUserVerify.setId(userVerify.getId());

        partialUpdatedUserVerify
            .rating(UPDATED_RATING)
            .school(UPDATED_SCHOOL)
            .studentID(UPDATED_STUDENT_ID)
            .major(UPDATED_MAJOR)
            .graduationYear(UPDATED_GRADUATION_YEAR);

        restUserVerifyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserVerify.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserVerify))
            )
            .andExpect(status().isOk());

        // Validate the UserVerify in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserVerifyUpdatableFieldsEquals(partialUpdatedUserVerify, getPersistedUserVerify(partialUpdatedUserVerify));
    }

    @Test
    @Transactional
    void patchNonExistingUserVerify() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userVerify.setId(longCount.incrementAndGet());

        // Create the UserVerify
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserVerifyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userVerifyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userVerifyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserVerify() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userVerify.setId(longCount.incrementAndGet());

        // Create the UserVerify
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserVerifyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userVerifyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserVerify() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userVerify.setId(longCount.incrementAndGet());

        // Create the UserVerify
        UserVerifyDTO userVerifyDTO = userVerifyMapper.toDto(userVerify);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserVerifyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(userVerifyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserVerify in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserVerify() throws Exception {
        // Initialize the database
        insertedUserVerify = userVerifyRepository.saveAndFlush(userVerify);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the userVerify
        restUserVerifyMockMvc
            .perform(delete(ENTITY_API_URL_ID, userVerify.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return userVerifyRepository.count();
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

    protected UserVerify getPersistedUserVerify(UserVerify userVerify) {
        return userVerifyRepository.findById(userVerify.getId()).orElseThrow();
    }

    protected void assertPersistedUserVerifyToMatchAllProperties(UserVerify expectedUserVerify) {
        assertUserVerifyAllPropertiesEquals(expectedUserVerify, getPersistedUserVerify(expectedUserVerify));
    }

    protected void assertPersistedUserVerifyToMatchUpdatableProperties(UserVerify expectedUserVerify) {
        assertUserVerifyAllUpdatablePropertiesEquals(expectedUserVerify, getPersistedUserVerify(expectedUserVerify));
    }
}
