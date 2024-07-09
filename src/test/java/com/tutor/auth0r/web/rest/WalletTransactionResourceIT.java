package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.WalletTransactionAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.domain.enumeration.WalletTransactionStatus;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import com.tutor.auth0r.repository.WalletTransactionRepository;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import com.tutor.auth0r.service.mapper.WalletTransactionMapper;
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
 * Integration tests for the {@link WalletTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WalletTransactionResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final WalletTransactionType DEFAULT_TYPE = WalletTransactionType.DEPOSIT;
    private static final WalletTransactionType UPDATED_TYPE = WalletTransactionType.WITHDRAWAL;

    private static final WalletTransactionStatus DEFAULT_STATUS = WalletTransactionStatus.SUCCEED;
    private static final WalletTransactionStatus UPDATED_STATUS = WalletTransactionStatus.FAILED;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/wallet-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private WalletTransactionMapper walletTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWalletTransactionMockMvc;

    private WalletTransaction walletTransaction;

    private WalletTransaction insertedWalletTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WalletTransaction createEntity(EntityManager em) {
        WalletTransaction walletTransaction = new WalletTransaction()
            .amount(DEFAULT_AMOUNT)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .createAt(DEFAULT_CREATE_AT);
        return walletTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WalletTransaction createUpdatedEntity(EntityManager em) {
        WalletTransaction walletTransaction = new WalletTransaction()
            .amount(UPDATED_AMOUNT)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .createAt(UPDATED_CREATE_AT);
        return walletTransaction;
    }

    @BeforeEach
    public void initTest() {
        walletTransaction = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedWalletTransaction != null) {
            walletTransactionRepository.delete(insertedWalletTransaction);
            insertedWalletTransaction = null;
        }
    }

    @Test
    @Transactional
    void createWalletTransaction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WalletTransaction
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);
        var returnedWalletTransactionDTO = om.readValue(
            restWalletTransactionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(walletTransactionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WalletTransactionDTO.class
        );

        // Validate the WalletTransaction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWalletTransaction = walletTransactionMapper.toEntity(returnedWalletTransactionDTO);
        assertWalletTransactionUpdatableFieldsEquals(returnedWalletTransaction, getPersistedWalletTransaction(returnedWalletTransaction));

        insertedWalletTransaction = returnedWalletTransaction;
    }

    @Test
    @Transactional
    void createWalletTransactionWithExistingId() throws Exception {
        // Create the WalletTransaction with an existing ID
        walletTransaction.setId(1L);
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletTransactionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(walletTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWalletTransactions() throws Exception {
        // Initialize the database
        insertedWalletTransaction = walletTransactionRepository.saveAndFlush(walletTransaction);

        // Get all the walletTransactionList
        restWalletTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())));
    }

    @Test
    @Transactional
    void getWalletTransaction() throws Exception {
        // Initialize the database
        insertedWalletTransaction = walletTransactionRepository.saveAndFlush(walletTransaction);

        // Get the walletTransaction
        restWalletTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, walletTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(walletTransaction.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWalletTransaction() throws Exception {
        // Get the walletTransaction
        restWalletTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWalletTransaction() throws Exception {
        // Initialize the database
        insertedWalletTransaction = walletTransactionRepository.saveAndFlush(walletTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the walletTransaction
        WalletTransaction updatedWalletTransaction = walletTransactionRepository.findById(walletTransaction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWalletTransaction are not directly saved in db
        em.detach(updatedWalletTransaction);
        updatedWalletTransaction.amount(UPDATED_AMOUNT).type(UPDATED_TYPE).status(UPDATED_STATUS).createAt(UPDATED_CREATE_AT);
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(updatedWalletTransaction);

        restWalletTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, walletTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(walletTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWalletTransactionToMatchAllProperties(updatedWalletTransaction);
    }

    @Test
    @Transactional
    void putNonExistingWalletTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        walletTransaction.setId(longCount.incrementAndGet());

        // Create the WalletTransaction
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, walletTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(walletTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWalletTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        walletTransaction.setId(longCount.incrementAndGet());

        // Create the WalletTransaction
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(walletTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWalletTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        walletTransaction.setId(longCount.incrementAndGet());

        // Create the WalletTransaction
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletTransactionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(walletTransactionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWalletTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedWalletTransaction = walletTransactionRepository.saveAndFlush(walletTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the walletTransaction using partial update
        WalletTransaction partialUpdatedWalletTransaction = new WalletTransaction();
        partialUpdatedWalletTransaction.setId(walletTransaction.getId());

        partialUpdatedWalletTransaction.type(UPDATED_TYPE).status(UPDATED_STATUS).createAt(UPDATED_CREATE_AT);

        restWalletTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWalletTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWalletTransaction))
            )
            .andExpect(status().isOk());

        // Validate the WalletTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWalletTransactionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWalletTransaction, walletTransaction),
            getPersistedWalletTransaction(walletTransaction)
        );
    }

    @Test
    @Transactional
    void fullUpdateWalletTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedWalletTransaction = walletTransactionRepository.saveAndFlush(walletTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the walletTransaction using partial update
        WalletTransaction partialUpdatedWalletTransaction = new WalletTransaction();
        partialUpdatedWalletTransaction.setId(walletTransaction.getId());

        partialUpdatedWalletTransaction.amount(UPDATED_AMOUNT).type(UPDATED_TYPE).status(UPDATED_STATUS).createAt(UPDATED_CREATE_AT);

        restWalletTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWalletTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWalletTransaction))
            )
            .andExpect(status().isOk());

        // Validate the WalletTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWalletTransactionUpdatableFieldsEquals(
            partialUpdatedWalletTransaction,
            getPersistedWalletTransaction(partialUpdatedWalletTransaction)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWalletTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        walletTransaction.setId(longCount.incrementAndGet());

        // Create the WalletTransaction
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, walletTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(walletTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWalletTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        walletTransaction.setId(longCount.incrementAndGet());

        // Create the WalletTransaction
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(walletTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWalletTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        walletTransaction.setId(longCount.incrementAndGet());

        // Create the WalletTransaction
        WalletTransactionDTO walletTransactionDTO = walletTransactionMapper.toDto(walletTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWalletTransactionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(walletTransactionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WalletTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWalletTransaction() throws Exception {
        // Initialize the database
        insertedWalletTransaction = walletTransactionRepository.saveAndFlush(walletTransaction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the walletTransaction
        restWalletTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, walletTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return walletTransactionRepository.count();
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

    protected WalletTransaction getPersistedWalletTransaction(WalletTransaction walletTransaction) {
        return walletTransactionRepository.findById(walletTransaction.getId()).orElseThrow();
    }

    protected void assertPersistedWalletTransactionToMatchAllProperties(WalletTransaction expectedWalletTransaction) {
        assertWalletTransactionAllPropertiesEquals(expectedWalletTransaction, getPersistedWalletTransaction(expectedWalletTransaction));
    }

    protected void assertPersistedWalletTransactionToMatchUpdatableProperties(WalletTransaction expectedWalletTransaction) {
        assertWalletTransactionAllUpdatablePropertiesEquals(
            expectedWalletTransaction,
            getPersistedWalletTransaction(expectedWalletTransaction)
        );
    }
}
