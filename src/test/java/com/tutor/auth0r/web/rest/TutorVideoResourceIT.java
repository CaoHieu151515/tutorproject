package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.TutorVideoAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.TutorVideo;
import com.tutor.auth0r.repository.TutorVideoRepository;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
import com.tutor.auth0r.service.mapper.TutorVideoMapper;
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
 * Integration tests for the {@link TutorVideoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TutorVideoResourceIT {

    private static final String ENTITY_API_URL = "/api/tutor-videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TutorVideoRepository tutorVideoRepository;

    @Autowired
    private TutorVideoMapper tutorVideoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTutorVideoMockMvc;

    private TutorVideo tutorVideo;

    private TutorVideo insertedTutorVideo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TutorVideo createEntity(EntityManager em) {
        TutorVideo tutorVideo = new TutorVideo();
        return tutorVideo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TutorVideo createUpdatedEntity(EntityManager em) {
        TutorVideo tutorVideo = new TutorVideo();
        return tutorVideo;
    }

    @BeforeEach
    public void initTest() {
        tutorVideo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTutorVideo != null) {
            tutorVideoRepository.delete(insertedTutorVideo);
            insertedTutorVideo = null;
        }
    }

    @Test
    @Transactional
    void createTutorVideo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TutorVideo
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);
        var returnedTutorVideoDTO = om.readValue(
            restTutorVideoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorVideoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TutorVideoDTO.class
        );

        // Validate the TutorVideo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTutorVideo = tutorVideoMapper.toEntity(returnedTutorVideoDTO);
        assertTutorVideoUpdatableFieldsEquals(returnedTutorVideo, getPersistedTutorVideo(returnedTutorVideo));

        insertedTutorVideo = returnedTutorVideo;
    }

    @Test
    @Transactional
    void createTutorVideoWithExistingId() throws Exception {
        // Create the TutorVideo with an existing ID
        tutorVideo.setId(1L);
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTutorVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorVideoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTutorVideos() throws Exception {
        // Initialize the database
        insertedTutorVideo = tutorVideoRepository.saveAndFlush(tutorVideo);

        // Get all the tutorVideoList
        restTutorVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tutorVideo.getId().intValue())));
    }

    @Test
    @Transactional
    void getTutorVideo() throws Exception {
        // Initialize the database
        insertedTutorVideo = tutorVideoRepository.saveAndFlush(tutorVideo);

        // Get the tutorVideo
        restTutorVideoMockMvc
            .perform(get(ENTITY_API_URL_ID, tutorVideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tutorVideo.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTutorVideo() throws Exception {
        // Get the tutorVideo
        restTutorVideoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTutorVideo() throws Exception {
        // Initialize the database
        insertedTutorVideo = tutorVideoRepository.saveAndFlush(tutorVideo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorVideo
        TutorVideo updatedTutorVideo = tutorVideoRepository.findById(tutorVideo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTutorVideo are not directly saved in db
        em.detach(updatedTutorVideo);
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(updatedTutorVideo);

        restTutorVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorVideoDTO))
            )
            .andExpect(status().isOk());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTutorVideoToMatchAllProperties(updatedTutorVideo);
    }

    @Test
    @Transactional
    void putNonExistingTutorVideo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorVideo.setId(longCount.incrementAndGet());

        // Create the TutorVideo
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTutorVideo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorVideo.setId(longCount.incrementAndGet());

        // Create the TutorVideo
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tutorVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTutorVideo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorVideo.setId(longCount.incrementAndGet());

        // Create the TutorVideo
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorVideoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tutorVideoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTutorVideoWithPatch() throws Exception {
        // Initialize the database
        insertedTutorVideo = tutorVideoRepository.saveAndFlush(tutorVideo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorVideo using partial update
        TutorVideo partialUpdatedTutorVideo = new TutorVideo();
        partialUpdatedTutorVideo.setId(tutorVideo.getId());

        restTutorVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutorVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTutorVideo))
            )
            .andExpect(status().isOk());

        // Validate the TutorVideo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTutorVideoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTutorVideo, tutorVideo),
            getPersistedTutorVideo(tutorVideo)
        );
    }

    @Test
    @Transactional
    void fullUpdateTutorVideoWithPatch() throws Exception {
        // Initialize the database
        insertedTutorVideo = tutorVideoRepository.saveAndFlush(tutorVideo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tutorVideo using partial update
        TutorVideo partialUpdatedTutorVideo = new TutorVideo();
        partialUpdatedTutorVideo.setId(tutorVideo.getId());

        restTutorVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutorVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTutorVideo))
            )
            .andExpect(status().isOk());

        // Validate the TutorVideo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTutorVideoUpdatableFieldsEquals(partialUpdatedTutorVideo, getPersistedTutorVideo(partialUpdatedTutorVideo));
    }

    @Test
    @Transactional
    void patchNonExistingTutorVideo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorVideo.setId(longCount.incrementAndGet());

        // Create the TutorVideo
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tutorVideoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tutorVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTutorVideo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorVideo.setId(longCount.incrementAndGet());

        // Create the TutorVideo
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tutorVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTutorVideo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tutorVideo.setId(longCount.incrementAndGet());

        // Create the TutorVideo
        TutorVideoDTO tutorVideoDTO = tutorVideoMapper.toDto(tutorVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorVideoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tutorVideoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TutorVideo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTutorVideo() throws Exception {
        // Initialize the database
        insertedTutorVideo = tutorVideoRepository.saveAndFlush(tutorVideo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tutorVideo
        restTutorVideoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tutorVideo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tutorVideoRepository.count();
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

    protected TutorVideo getPersistedTutorVideo(TutorVideo tutorVideo) {
        return tutorVideoRepository.findById(tutorVideo.getId()).orElseThrow();
    }

    protected void assertPersistedTutorVideoToMatchAllProperties(TutorVideo expectedTutorVideo) {
        assertTutorVideoAllPropertiesEquals(expectedTutorVideo, getPersistedTutorVideo(expectedTutorVideo));
    }

    protected void assertPersistedTutorVideoToMatchUpdatableProperties(TutorVideo expectedTutorVideo) {
        assertTutorVideoAllUpdatablePropertiesEquals(expectedTutorVideo, getPersistedTutorVideo(expectedTutorVideo));
    }
}
