package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.TutorAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static com.tutor.auth0r.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.enumeration.Devide;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.mapper.TutorMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link TutorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TutorResourceIT {
    // private static final Boolean DEFAULT_RECOMMEND = false;
    // private static final Boolean UPDATED_RECOMMEND = true;

    // private static final Double DEFAULT_PRICE = 1D;
    // private static final Double UPDATED_PRICE = 2D;

    // private static final Devide DEFAULT_TU_DEVICE = Devide.MICRO;
    // private static final Devide UPDATED_TU_DEVICE = Devide.CAMERA;

    // private static final TuStatus DEFAULT_STATUS = TuStatus.READY;
    // private static final TuStatus UPDATED_STATUS = TuStatus.BUSY;

    // private static final Long DEFAULT_FOLLOWER_COUNT = 1L;
    // private static final Long UPDATED_FOLLOWER_COUNT = 2L;

    // private static final BigDecimal DEFAULT_AVERAGE_RATING = new BigDecimal(1);
    // private static final BigDecimal UPDATED_AVERAGE_RATING = new BigDecimal(2);

    // private static final String ENTITY_API_URL = "/api/tutors";
    // private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    // private static Random random = new Random();
    // private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    // @Autowired
    // private ObjectMapper om;

    // @Autowired
    // private TutorRepository tutorRepository;

    // @Autowired
    // private TutorMapper tutorMapper;

    // @Autowired
    // private EntityManager em;

    // @Autowired
    // private MockMvc restTutorMockMvc;

    // private Tutor tutor;

    // private Tutor insertedTutor;

    // /**
    //  * Create an entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Tutor createEntity(EntityManager em) {
    //     Tutor tutor = new Tutor()
    //         .recommend(DEFAULT_RECOMMEND)
    //         .price(DEFAULT_PRICE)
    //         .tuDevice(DEFAULT_TU_DEVICE)
    //         .status(DEFAULT_STATUS)
    //         .followerCount(DEFAULT_FOLLOWER_COUNT)
    //         .averageRating(DEFAULT_AVERAGE_RATING);
    //     return tutor;
    // }

    // /**
    //  * Create an updated entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Tutor createUpdatedEntity(EntityManager em) {
    //     Tutor tutor = new Tutor()
    //         .recommend(UPDATED_RECOMMEND)
    //         .price(UPDATED_PRICE)
    //         .tuDevice(UPDATED_TU_DEVICE)
    //         .status(UPDATED_STATUS)
    //         .followerCount(UPDATED_FOLLOWER_COUNT)
    //         .averageRating(UPDATED_AVERAGE_RATING);
    //     return tutor;
    // }

    // @BeforeEach
    // public void initTest() {
    //     tutor = createEntity(em);
    // }

    // @AfterEach
    // public void cleanup() {
    //     if (insertedTutor != null) {
    //         tutorRepository.delete(insertedTutor);
    //         insertedTutor = null;
    //     }
    // }

    // @Test
    // @Transactional
    // void createTutor() throws Exception {
    //     long databaseSizeBeforeCreate = getRepositoryCount();
    //     // Create the Tutor
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);
    //     var returnedTutorDTO = om.readValue(
    //         restTutorMockMvc
    //             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDTO)))
    //             .andExpect(status().isCreated())
    //             .andReturn()
    //             .getResponse()
    //             .getContentAsString(),
    //         TutorDTO.class
    //     );

    //     // Validate the Tutor in the database
    //     assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    //     var returnedTutor = tutorMapper.toEntity(returnedTutorDTO);
    //     assertTutorUpdatableFieldsEquals(returnedTutor, getPersistedTutor(returnedTutor));

    //     insertedTutor = returnedTutor;
    // }

    // @Test
    // @Transactional
    // void createTutorWithExistingId() throws Exception {
    //     // Create the Tutor with an existing ID
    //     tutor.setId(1L);
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);

    //     long databaseSizeBeforeCreate = getRepositoryCount();

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restTutorMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDTO)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // void getAllTutors() throws Exception {
    //     // Initialize the database
    //     insertedTutor = tutorRepository.saveAndFlush(tutor);

    //     // Get all the tutorList
    //     restTutorMockMvc
    //         .perform(get(ENTITY_API_URL + "?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(tutor.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].recommend").value(hasItem(DEFAULT_RECOMMEND.booleanValue())))
    //         .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
    //         .andExpect(jsonPath("$.[*].tuDevice").value(hasItem(DEFAULT_TU_DEVICE.toString())))
    //         .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
    //         .andExpect(jsonPath("$.[*].followerCount").value(hasItem(DEFAULT_FOLLOWER_COUNT.intValue())))
    //         .andExpect(jsonPath("$.[*].averageRating").value(hasItem(sameNumber(DEFAULT_AVERAGE_RATING))));
    // }

    // @Test
    // @Transactional
    // void getTutor() throws Exception {
    //     // Initialize the database
    //     insertedTutor = tutorRepository.saveAndFlush(tutor);

    //     // Get the tutor
    //     restTutorMockMvc
    //         .perform(get(ENTITY_API_URL_ID, tutor.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.id").value(tutor.getId().intValue()))
    //         .andExpect(jsonPath("$.recommend").value(DEFAULT_RECOMMEND.booleanValue()))
    //         .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
    //         .andExpect(jsonPath("$.tuDevice").value(DEFAULT_TU_DEVICE.toString()))
    //         .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
    //         .andExpect(jsonPath("$.followerCount").value(DEFAULT_FOLLOWER_COUNT.intValue()))
    //         .andExpect(jsonPath("$.averageRating").value(sameNumber(DEFAULT_AVERAGE_RATING)));
    // }

    // @Test
    // @Transactional
    // void getNonExistingTutor() throws Exception {
    //     // Get the tutor
    //     restTutorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // void putExistingTutor() throws Exception {
    //     // Initialize the database
    //     insertedTutor = tutorRepository.saveAndFlush(tutor);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the tutor
    //     Tutor updatedTutor = tutorRepository.findById(tutor.getId()).orElseThrow();
    //     // Disconnect from session so that the updates on updatedTutor are not directly saved in db
    //     em.detach(updatedTutor);
    //     updatedTutor
    //         .recommend(UPDATED_RECOMMEND)
    //         .price(UPDATED_PRICE)
    //         .tuDevice(UPDATED_TU_DEVICE)
    //         .status(UPDATED_STATUS)
    //         .followerCount(UPDATED_FOLLOWER_COUNT)
    //         .averageRating(UPDATED_AVERAGE_RATING);
    //     TutorDTO tutorDTO = tutorMapper.toDto(updatedTutor);

    //     restTutorMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, tutorDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDTO))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertPersistedTutorToMatchAllProperties(updatedTutor);
    // }

    // @Test
    // @Transactional
    // void putNonExistingTutor() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutor.setId(longCount.incrementAndGet());

    //     // Create the Tutor
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restTutorMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, tutorDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchTutor() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutor.setId(longCount.incrementAndGet());

    //     // Create the Tutor
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, longCount.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(om.writeValueAsBytes(tutorDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithMissingIdPathParamTutor() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutor.setId(longCount.incrementAndGet());

    //     // Create the Tutor
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorMockMvc
    //         .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorDTO)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void partialUpdateTutorWithPatch() throws Exception {
    //     // Initialize the database
    //     insertedTutor = tutorRepository.saveAndFlush(tutor);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the tutor using partial update
    //     Tutor partialUpdatedTutor = new Tutor();
    //     partialUpdatedTutor.setId(tutor.getId());

    //     partialUpdatedTutor.price(UPDATED_PRICE).tuDevice(UPDATED_TU_DEVICE).status(UPDATED_STATUS).followerCount(UPDATED_FOLLOWER_COUNT);

    //     restTutorMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedTutor.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(partialUpdatedTutor))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Tutor in the database

    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertTutorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTutor, tutor), getPersistedTutor(tutor));
    // }

    // @Test
    // @Transactional
    // void fullUpdateTutorWithPatch() throws Exception {
    //     // Initialize the database
    //     insertedTutor = tutorRepository.saveAndFlush(tutor);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the tutor using partial update
    //     Tutor partialUpdatedTutor = new Tutor();
    //     partialUpdatedTutor.setId(tutor.getId());

    //     partialUpdatedTutor
    //         .recommend(UPDATED_RECOMMEND)
    //         .price(UPDATED_PRICE)
    //         .tuDevice(UPDATED_TU_DEVICE)
    //         .status(UPDATED_STATUS)
    //         .followerCount(UPDATED_FOLLOWER_COUNT)
    //         .averageRating(UPDATED_AVERAGE_RATING);

    //     restTutorMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedTutor.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(partialUpdatedTutor))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Tutor in the database

    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertTutorUpdatableFieldsEquals(partialUpdatedTutor, getPersistedTutor(partialUpdatedTutor));
    // }

    // @Test
    // @Transactional
    // void patchNonExistingTutor() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutor.setId(longCount.incrementAndGet());

    //     // Create the Tutor
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restTutorMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, tutorDTO.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(tutorDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithIdMismatchTutor() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutor.setId(longCount.incrementAndGet());

    //     // Create the Tutor
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(tutorDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithMissingIdPathParamTutor() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutor.setId(longCount.incrementAndGet());

    //     // Create the Tutor
    //     TutorDTO tutorDTO = tutorMapper.toDto(tutor);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorMockMvc
    //         .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tutorDTO)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Tutor in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void deleteTutor() throws Exception {
    //     // Initialize the database
    //     insertedTutor = tutorRepository.saveAndFlush(tutor);

    //     long databaseSizeBeforeDelete = getRepositoryCount();

    //     // Delete the tutor
    //     restTutorMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, tutor.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    // }

    // protected long getRepositoryCount() {
    //     return tutorRepository.count();
    // }

    // protected void assertIncrementedRepositoryCount(long countBefore) {
    //     assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    // }

    // protected void assertDecrementedRepositoryCount(long countBefore) {
    //     assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    // }

    // protected void assertSameRepositoryCount(long countBefore) {
    //     assertThat(countBefore).isEqualTo(getRepositoryCount());
    // }

    // protected Tutor getPersistedTutor(Tutor tutor) {
    //     return tutorRepository.findById(tutor.getId()).orElseThrow();
    // }

    // protected void assertPersistedTutorToMatchAllProperties(Tutor expectedTutor) {
    //     assertTutorAllPropertiesEquals(expectedTutor, getPersistedTutor(expectedTutor));
    // }

    // protected void assertPersistedTutorToMatchUpdatableProperties(Tutor expectedTutor) {
    //     assertTutorAllUpdatablePropertiesEquals(expectedTutor, getPersistedTutor(expectedTutor));
    // }
}
