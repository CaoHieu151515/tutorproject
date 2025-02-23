package com.tutor.auth0r.service;

import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.service.dto.CustomDTO.WalletHistoryDTO;
import com.tutor.auth0r.service.dto.WalletDTO;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tutor.auth0r.domain.Wallet}.
 */
public interface WalletService {
    Wallet getAdminWallet();

    Wallet getWalletByUserLogin(String login);

    Wallet getCurrentUserWallet();

    List<WalletTransactionDTO> getWalletTransactionsByCurrentUserWallet();

    Wallet save(Wallet wallet);

    /**
     * Save a wallet.
     *
     * @param walletDTO the entity to save.
     * @return the persisted entity.
     */
    WalletDTO save(WalletDTO walletDTO);

    /**
     * Updates a wallet.
     *
     * @param walletDTO the entity to update.
     * @return the persisted entity.
     */
    WalletDTO update(WalletDTO walletDTO);

    /**
     * Partially updates a wallet.
     *
     * @param walletDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WalletDTO> partialUpdate(WalletDTO walletDTO);

    /**
     * Get all the wallets.
     *
     * @return the list of entities.
     */
    List<WalletDTO> findAll();

    /**
     * Get the "id" wallet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WalletDTO> findOne(Long id);

    /**
     * Delete the "id" wallet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    WalletHistoryDTO getWalletHistoryByCurrentUser();
}
