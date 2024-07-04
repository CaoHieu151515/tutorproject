package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.IdentityCardAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.repository.IdentityCardRepository;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.mapper.IdentityCardMapper;
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
 * Integration tests for the {@link IdentityCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IdentityCardResourceIT {

    private static final String ENTITY_API_URL = "/api/identity-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IdentityCardRepository identityCardRepository;

    @Autowired
    private IdentityCardMapper identityCardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdentityCardMockMvc;

    private IdentityCard identityCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentityCard createEntity(EntityManager em) {
        IdentityCard identityCard = new IdentityCard();
        return identityCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentityCard createUpdatedEntity(EntityManager em) {
        IdentityCard identityCard = new IdentityCard();
        return identityCard;
    }

    @BeforeEach
    public void initTest() {
        identityCard = createEntity(em);
    }

    @Test
    @Transactional
    void createIdentityCard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IdentityCard
        IdentityCardDTO identityCardDTO = identityCardMapper.toDto(identityCard);
        var returnedIdentityCardDTO = om.readValue(
            restIdentityCardMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(identityCardDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IdentityCardDTO.class
        );

        // Validate the IdentityCard in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIdentityCard = identityCardMapper.toEntity(returnedIdentityCardDTO);
        assertIdentityCardUpdatableFieldsEquals(returnedIdentityCard, getPersistedIdentityCard(returnedIdentityCard));
    }

    @Test
    @Transactional
    void createIdentityCardWithExistingId() throws Exception {
        // Create the IdentityCard with an existing ID
        identityCard.setId(1L);
        IdentityCardDTO identityCardDTO = identityCardMapper.toDto(identityCard);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentityCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(identityCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IdentityCard in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIdentityCards() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        // Get all the identityCardList
        restIdentityCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identityCard.getId().intValue())));
    }

    @Test
    @Transactional
    void getIdentityCard() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        // Get the identityCard
        restIdentityCardMockMvc
            .perform(get(ENTITY_API_URL_ID, identityCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identityCard.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingIdentityCard() throws Exception {
        // Get the identityCard
        restIdentityCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteIdentityCard() throws Exception {
        // Initialize the database
        identityCardRepository.saveAndFlush(identityCard);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the identityCard
        restIdentityCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, identityCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return identityCardRepository.count();
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

    protected IdentityCard getPersistedIdentityCard(IdentityCard identityCard) {
        return identityCardRepository.findById(identityCard.getId()).orElseThrow();
    }

    protected void assertPersistedIdentityCardToMatchAllProperties(IdentityCard expectedIdentityCard) {
        assertIdentityCardAllPropertiesEquals(expectedIdentityCard, getPersistedIdentityCard(expectedIdentityCard));
    }

    protected void assertPersistedIdentityCardToMatchUpdatableProperties(IdentityCard expectedIdentityCard) {
        assertIdentityCardAllUpdatablePropertiesEquals(expectedIdentityCard, getPersistedIdentityCard(expectedIdentityCard));
    }
}
