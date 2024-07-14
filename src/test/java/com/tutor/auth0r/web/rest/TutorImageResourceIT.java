package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.TutorImageAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.TutorImage;
import com.tutor.auth0r.repository.TutorImageRepository;
import com.tutor.auth0r.service.dto.TutorImageDTO;
import com.tutor.auth0r.service.mapper.TutorImageMapper;
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
 * Integration tests for the {@link TutorImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TutorImageResourceIT {
    // private static final String ENTITY_API_URL = "/api/tutor-images";
    // private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    // private static Random random = new Random();
    // private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    // @Autowired
    // private ObjectMapper om;

    // @Autowired
    // private TutorImageRepository tutorImageRepository;

    // @Autowired
    // private TutorImageMapper tutorImageMapper;

    // @Autowired
    // private EntityManager em;

    // @Autowired
    // private MockMvc restTutorImageMockMvc;

    // private TutorImage tutorImage;

    // private TutorImage insertedTutorImage;

    // /**
    //  * Create an entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static TutorImage createEntity(EntityManager em) {
    //     TutorImage tutorImage = new TutorImage();
    //     return tutorImage;
    // }

    // /**
    //  * Create an updated entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static TutorImage createUpdatedEntity(EntityManager em) {
    //     TutorImage tutorImage = new TutorImage();
    //     return tutorImage;
    // }

    // @BeforeEach
    // public void initTest() {
    //     tutorImage = createEntity(em);
    // }

    // @AfterEach
    // public void cleanup() {
    //     if (insertedTutorImage != null) {
    //         tutorImageRepository.delete(insertedTutorImage);
    //         insertedTutorImage = null;
    //     }
    // }

    // @Test
    // @Transactional
    // void createTutorImage() throws Exception {
    //     long databaseSizeBeforeCreate = getRepositoryCount();
    //     // Create the TutorImage
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);
    //     var returnedTutorImageDTO = om.readValue(
    //         restTutorImageMockMvc
    //             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorImageDTO)))
    //             .andExpect(status().isCreated())
    //             .andReturn()
    //             .getResponse()
    //             .getContentAsString(),
    //         TutorImageDTO.class
    //     );

    //     // Validate the TutorImage in the database
    //     assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    //     var returnedTutorImage = tutorImageMapper.toEntity(returnedTutorImageDTO);
    //     assertTutorImageUpdatableFieldsEquals(returnedTutorImage, getPersistedTutorImage(returnedTutorImage));

    //     insertedTutorImage = returnedTutorImage;
    // }

    // @Test
    // @Transactional
    // void createTutorImageWithExistingId() throws Exception {
    //     // Create the TutorImage with an existing ID
    //     tutorImage.setId(1L);
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);

    //     long databaseSizeBeforeCreate = getRepositoryCount();

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restTutorImageMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorImageDTO)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // void getAllTutorImages() throws Exception {
    //     // Initialize the database
    //     insertedTutorImage = tutorImageRepository.saveAndFlush(tutorImage);

    //     // Get all the tutorImageList
    //     restTutorImageMockMvc
    //         .perform(get(ENTITY_API_URL + "?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(tutorImage.getId().intValue())));
    // }

    // @Test
    // @Transactional
    // void getTutorImage() throws Exception {
    //     // Initialize the database
    //     insertedTutorImage = tutorImageRepository.saveAndFlush(tutorImage);

    //     // Get the tutorImage
    //     restTutorImageMockMvc
    //         .perform(get(ENTITY_API_URL_ID, tutorImage.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.id").value(tutorImage.getId().intValue()));
    // }

    // @Test
    // @Transactional
    // void getNonExistingTutorImage() throws Exception {
    //     // Get the tutorImage
    //     restTutorImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // void putExistingTutorImage() throws Exception {
    //     // Initialize the database
    //     insertedTutorImage = tutorImageRepository.saveAndFlush(tutorImage);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the tutorImage
    //     TutorImage updatedTutorImage = tutorImageRepository.findById(tutorImage.getId()).orElseThrow();
    //     // Disconnect from session so that the updates on updatedTutorImage are not directly saved in db
    //     em.detach(updatedTutorImage);
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(updatedTutorImage);

    //     restTutorImageMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, tutorImageDTO.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(om.writeValueAsBytes(tutorImageDTO))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertPersistedTutorImageToMatchAllProperties(updatedTutorImage);
    // }

    // @Test
    // @Transactional
    // void putNonExistingTutorImage() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutorImage.setId(longCount.incrementAndGet());

    //     // Create the TutorImage
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restTutorImageMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, tutorImageDTO.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(om.writeValueAsBytes(tutorImageDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchTutorImage() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutorImage.setId(longCount.incrementAndGet());

    //     // Create the TutorImage
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorImageMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, longCount.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(om.writeValueAsBytes(tutorImageDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithMissingIdPathParamTutorImage() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutorImage.setId(longCount.incrementAndGet());

    //     // Create the TutorImage
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorImageMockMvc
    //         .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorImageDTO)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void partialUpdateTutorImageWithPatch() throws Exception {
    //     // Initialize the database
    //     insertedTutorImage = tutorImageRepository.saveAndFlush(tutorImage);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the tutorImage using partial update
    //     TutorImage partialUpdatedTutorImage = new TutorImage();
    //     partialUpdatedTutorImage.setId(tutorImage.getId());

    //     restTutorImageMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedTutorImage.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(partialUpdatedTutorImage))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the TutorImage in the database

    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertTutorImageUpdatableFieldsEquals(
    //         createUpdateProxyForBean(partialUpdatedTutorImage, tutorImage),
    //         getPersistedTutorImage(tutorImage)
    //     );
    // }

    // @Test
    // @Transactional
    // void fullUpdateTutorImageWithPatch() throws Exception {
    //     // Initialize the database
    //     insertedTutorImage = tutorImageRepository.saveAndFlush(tutorImage);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the tutorImage using partial update
    //     TutorImage partialUpdatedTutorImage = new TutorImage();
    //     partialUpdatedTutorImage.setId(tutorImage.getId());

    //     restTutorImageMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedTutorImage.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(partialUpdatedTutorImage))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the TutorImage in the database

    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertTutorImageUpdatableFieldsEquals(partialUpdatedTutorImage, getPersistedTutorImage(partialUpdatedTutorImage));
    // }

    // @Test
    // @Transactional
    // void patchNonExistingTutorImage() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutorImage.setId(longCount.incrementAndGet());

    //     // Create the TutorImage
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restTutorImageMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, tutorImageDTO.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(tutorImageDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithIdMismatchTutorImage() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutorImage.setId(longCount.incrementAndGet());

    //     // Create the TutorImage
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorImageMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(tutorImageDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithMissingIdPathParamTutorImage() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     tutorImage.setId(longCount.incrementAndGet());

    //     // Create the TutorImage
    //     TutorImageDTO tutorImageDTO = tutorImageMapper.toDto(tutorImage);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restTutorImageMockMvc
    //         .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tutorImageDTO)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the TutorImage in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void deleteTutorImage() throws Exception {
    //     // Initialize the database
    //     insertedTutorImage = tutorImageRepository.saveAndFlush(tutorImage);

    //     long databaseSizeBeforeDelete = getRepositoryCount();

    //     // Delete the tutorImage
    //     restTutorImageMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, tutorImage.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    // }

    // protected long getRepositoryCount() {
    //     return tutorImageRepository.count();
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

    // protected TutorImage getPersistedTutorImage(TutorImage tutorImage) {
    //     return tutorImageRepository.findById(tutorImage.getId()).orElseThrow();
    // }

    // protected void assertPersistedTutorImageToMatchAllProperties(TutorImage expectedTutorImage) {
    //     assertTutorImageAllPropertiesEquals(expectedTutorImage, getPersistedTutorImage(expectedTutorImage));
    // }

    // protected void assertPersistedTutorImageToMatchUpdatableProperties(TutorImage expectedTutorImage) {
    //     assertTutorImageAllUpdatablePropertiesEquals(expectedTutorImage, getPersistedTutorImage(expectedTutorImage));
    // }
}
