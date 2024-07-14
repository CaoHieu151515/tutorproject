package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.ThirdPartyTransactionAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.ThirdPartyTransaction;
import com.tutor.auth0r.repository.ThirdPartyTransactionRepository;
import com.tutor.auth0r.service.dto.ThirdPartyTransactionDTO;
import com.tutor.auth0r.service.mapper.ThirdPartyTransactionMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ThirdPartyTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThirdPartyTransactionResourceIT {

    private static final String DEFAULT_THIRD_PARTY_ID = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_PARTY_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/third-party-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThirdPartyTransactionRepository thirdPartyTransactionRepository;

    @Autowired
    private ThirdPartyTransactionMapper thirdPartyTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThirdPartyTransactionMockMvc;

    private ThirdPartyTransaction thirdPartyTransaction;

    private ThirdPartyTransaction insertedThirdPartyTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThirdPartyTransaction createEntity(EntityManager em) {
        ThirdPartyTransaction thirdPartyTransaction = new ThirdPartyTransaction()
            .thirdPartyId(DEFAULT_THIRD_PARTY_ID)
            .transactionDate(DEFAULT_TRANSACTION_DATE);
        return thirdPartyTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThirdPartyTransaction createUpdatedEntity(EntityManager em) {
        ThirdPartyTransaction thirdPartyTransaction = new ThirdPartyTransaction()
            .thirdPartyId(UPDATED_THIRD_PARTY_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE);
        return thirdPartyTransaction;
    }

    @BeforeEach
    public void initTest() {
        thirdPartyTransaction = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedThirdPartyTransaction != null) {
            thirdPartyTransactionRepository.delete(insertedThirdPartyTransaction);
            insertedThirdPartyTransaction = null;
        }
    }

    @Test
    @Transactional
    void createThirdPartyTransaction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ThirdPartyTransaction
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);
        var returnedThirdPartyTransactionDTO = om.readValue(
            restThirdPartyTransactionMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyTransactionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ThirdPartyTransactionDTO.class
        );

        // Validate the ThirdPartyTransaction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedThirdPartyTransaction = thirdPartyTransactionMapper.toEntity(returnedThirdPartyTransactionDTO);
        assertThirdPartyTransactionUpdatableFieldsEquals(
            returnedThirdPartyTransaction,
            getPersistedThirdPartyTransaction(returnedThirdPartyTransaction)
        );

        insertedThirdPartyTransaction = returnedThirdPartyTransaction;
    }

    @Test
    @Transactional
    void createThirdPartyTransactionWithExistingId() throws Exception {
        // Create the ThirdPartyTransaction with an existing ID
        thirdPartyTransaction.setId(1L);
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdPartyTransactionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkThirdPartyIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thirdPartyTransaction.setThirdPartyId(null);

        // Create the ThirdPartyTransaction, which fails.
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        restThirdPartyTransactionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyTransactionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thirdPartyTransaction.setTransactionDate(null);

        // Create the ThirdPartyTransaction, which fails.
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        restThirdPartyTransactionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyTransactionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThirdPartyTransactions() throws Exception {
        // Initialize the database
        insertedThirdPartyTransaction = thirdPartyTransactionRepository.saveAndFlush(thirdPartyTransaction);

        // Get all the thirdPartyTransactionList
        restThirdPartyTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdPartyTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].thirdPartyId").value(hasItem(DEFAULT_THIRD_PARTY_ID)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())));
    }

    @Test
    @Transactional
    void getThirdPartyTransaction() throws Exception {
        // Initialize the database
        insertedThirdPartyTransaction = thirdPartyTransactionRepository.saveAndFlush(thirdPartyTransaction);

        // Get the thirdPartyTransaction
        restThirdPartyTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, thirdPartyTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thirdPartyTransaction.getId().intValue()))
            .andExpect(jsonPath("$.thirdPartyId").value(DEFAULT_THIRD_PARTY_ID))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingThirdPartyTransaction() throws Exception {
        // Get the thirdPartyTransaction
        restThirdPartyTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThirdPartyTransaction() throws Exception {
        // Initialize the database
        insertedThirdPartyTransaction = thirdPartyTransactionRepository.saveAndFlush(thirdPartyTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thirdPartyTransaction
        ThirdPartyTransaction updatedThirdPartyTransaction = thirdPartyTransactionRepository
            .findById(thirdPartyTransaction.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedThirdPartyTransaction are not directly saved in db
        em.detach(updatedThirdPartyTransaction);
        updatedThirdPartyTransaction.thirdPartyId(UPDATED_THIRD_PARTY_ID).transactionDate(UPDATED_TRANSACTION_DATE);
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(updatedThirdPartyTransaction);

        restThirdPartyTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thirdPartyTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thirdPartyTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThirdPartyTransactionToMatchAllProperties(updatedThirdPartyTransaction);
    }

    @Test
    @Transactional
    void putNonExistingThirdPartyTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyTransaction.setId(longCount.incrementAndGet());

        // Create the ThirdPartyTransaction
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThirdPartyTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thirdPartyTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thirdPartyTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThirdPartyTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyTransaction.setId(longCount.incrementAndGet());

        // Create the ThirdPartyTransaction
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thirdPartyTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThirdPartyTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyTransaction.setId(longCount.incrementAndGet());

        // Create the ThirdPartyTransaction
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyTransactionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thirdPartyTransactionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThirdPartyTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedThirdPartyTransaction = thirdPartyTransactionRepository.saveAndFlush(thirdPartyTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thirdPartyTransaction using partial update
        ThirdPartyTransaction partialUpdatedThirdPartyTransaction = new ThirdPartyTransaction();
        partialUpdatedThirdPartyTransaction.setId(thirdPartyTransaction.getId());

        partialUpdatedThirdPartyTransaction.thirdPartyId(UPDATED_THIRD_PARTY_ID).transactionDate(UPDATED_TRANSACTION_DATE);

        restThirdPartyTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThirdPartyTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThirdPartyTransaction))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThirdPartyTransactionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedThirdPartyTransaction, thirdPartyTransaction),
            getPersistedThirdPartyTransaction(thirdPartyTransaction)
        );
    }

    @Test
    @Transactional
    void fullUpdateThirdPartyTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedThirdPartyTransaction = thirdPartyTransactionRepository.saveAndFlush(thirdPartyTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thirdPartyTransaction using partial update
        ThirdPartyTransaction partialUpdatedThirdPartyTransaction = new ThirdPartyTransaction();
        partialUpdatedThirdPartyTransaction.setId(thirdPartyTransaction.getId());

        partialUpdatedThirdPartyTransaction.thirdPartyId(UPDATED_THIRD_PARTY_ID).transactionDate(UPDATED_TRANSACTION_DATE);

        restThirdPartyTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThirdPartyTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThirdPartyTransaction))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThirdPartyTransactionUpdatableFieldsEquals(
            partialUpdatedThirdPartyTransaction,
            getPersistedThirdPartyTransaction(partialUpdatedThirdPartyTransaction)
        );
    }

    @Test
    @Transactional
    void patchNonExistingThirdPartyTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyTransaction.setId(longCount.incrementAndGet());

        // Create the ThirdPartyTransaction
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThirdPartyTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thirdPartyTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thirdPartyTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThirdPartyTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyTransaction.setId(longCount.incrementAndGet());

        // Create the ThirdPartyTransaction
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thirdPartyTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThirdPartyTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thirdPartyTransaction.setId(longCount.incrementAndGet());

        // Create the ThirdPartyTransaction
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = thirdPartyTransactionMapper.toDto(thirdPartyTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thirdPartyTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThirdPartyTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThirdPartyTransaction() throws Exception {
        // Initialize the database
        insertedThirdPartyTransaction = thirdPartyTransactionRepository.saveAndFlush(thirdPartyTransaction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thirdPartyTransaction
        restThirdPartyTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, thirdPartyTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return thirdPartyTransactionRepository.count();
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

    protected ThirdPartyTransaction getPersistedThirdPartyTransaction(ThirdPartyTransaction thirdPartyTransaction) {
        return thirdPartyTransactionRepository.findById(thirdPartyTransaction.getId()).orElseThrow();
    }

    protected void assertPersistedThirdPartyTransactionToMatchAllProperties(ThirdPartyTransaction expectedThirdPartyTransaction) {
        assertThirdPartyTransactionAllPropertiesEquals(
            expectedThirdPartyTransaction,
            getPersistedThirdPartyTransaction(expectedThirdPartyTransaction)
        );
    }

    protected void assertPersistedThirdPartyTransactionToMatchUpdatableProperties(ThirdPartyTransaction expectedThirdPartyTransaction) {
        assertThirdPartyTransactionAllUpdatablePropertiesEquals(
            expectedThirdPartyTransaction,
            getPersistedThirdPartyTransaction(expectedThirdPartyTransaction)
        );
    }
}
