package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import com.tutor.auth0r.repository.WalletRepository;
import com.tutor.auth0r.service.UserService;
import com.tutor.auth0r.service.WalletService;
import com.tutor.auth0r.service.dto.CustomDTO.WalletHistoryDTO;
import com.tutor.auth0r.service.dto.WalletDTO;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import com.tutor.auth0r.service.mapper.WalletMapper;
import com.tutor.auth0r.service.mapper.WalletTransactionMapper;
import com.tutor.auth0r.web.rest.errors.AdminWalletNotExistException;
import com.tutor.auth0r.web.rest.errors.NotLoggedException;
import com.tutor.auth0r.web.rest.errors.UserNotExistException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.Wallet}.
 */
@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;

    private final WalletMapper walletMapper;

    private final UserService userService;

    private final WalletTransactionMapper walletTransactionMapper;

    public WalletServiceImpl(
        WalletRepository walletRepository,
        WalletMapper walletMapper,
        UserService userService,
        WalletTransactionMapper walletTransactionMapper
    ) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.userService = userService;
        this.walletTransactionMapper = walletTransactionMapper;
    }

    @Override
    public WalletDTO save(WalletDTO walletDTO) {
        log.debug("Request to save Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    @Override
    public WalletDTO update(WalletDTO walletDTO) {
        log.debug("Request to update Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    @Override
    public Optional<WalletDTO> partialUpdate(WalletDTO walletDTO) {
        log.debug("Request to partially update Wallet : {}", walletDTO);

        return walletRepository
            .findById(walletDTO.getId())
            .map(existingWallet -> {
                walletMapper.partialUpdate(existingWallet, walletDTO);

                return existingWallet;
            })
            .map(walletRepository::save)
            .map(walletMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletDTO> findAll() {
        log.debug("Request to get all Wallets");
        return walletRepository.findAll().stream().map(walletMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletDTO> findOne(Long id) {
        log.debug("Request to get Wallet : {}", id);
        return walletRepository.findById(id).map(walletMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wallet : {}", id);
        walletRepository.deleteById(id);
    }

    public Wallet getAdminWallet() {
        Optional<Wallet> adminWalletOptional = walletRepository.findByAdmin();

        if (!adminWalletOptional.isPresent()) {
            throw new AdminWalletNotExistException();
        }

        return adminWalletOptional.get();
    }

    @Override
    public Wallet getWalletByUserLogin(String login) {
        Optional<User> userOptional = userService.getUserWithAuthoritiesByLogin(login);
        if (!userOptional.isPresent()) {
            throw new UserNotExistException();
        }

        User user = userOptional.get();

        log.debug("get wallet of user {} - id {}", user.getLogin(), user.getId());

        Optional<Wallet> walletOptional = walletRepository.findByUserLogin(user.getLogin());

        if (walletOptional.isPresent()) {
            return walletOptional.get();
        }

        //init new wallet if it doesnt exist

        Wallet wallet = new Wallet();
        wallet.setAmount(0d);
        wallet.getAppUser().getUser().setLogin(login);

        //push to database immedietly, even when badRequest of other service..
        wallet = walletRepository.saveAndFlush(wallet);

        return wallet;
    }

    @Override
    public Wallet getCurrentUserWallet() {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        log.debug("get wallet of user {} - id {}", userOptional.get().getLogin(), userOptional.get().getId());

        Optional<Wallet> walletOptional = walletRepository.findByUserIsCurrentUser();

        if (walletOptional.isPresent()) {
            return walletOptional.get();
        }

        //init new wallet if it doesnt exist

        Wallet wallet = new Wallet();
        wallet.setAmount(0d);
        wallet.getAppUser().setUser(userOptional.get());

        //push to database immedietly, even when badRequest of other service..
        wallet = walletRepository.saveAndFlush(wallet);

        return wallet;
    }

    @Override
    public List<WalletTransactionDTO> getWalletTransactionsByCurrentUserWallet() {
        Wallet wallet = getCurrentUserWallet();
        return wallet.getTransactions().stream().map(walletTransactionMapper::toDto).toList();
    }

    @Override
    public Wallet save(Wallet wallet) {
        log.debug("Request to save Wallet : {}", wallet);
        wallet = walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public WalletHistoryDTO getWalletHistoryByCurrentUser() {
        Wallet wallet = getCurrentUserWallet();

        Set<WalletTransactionDTO> hireTrans = wallet
            .getTransactions()
            .stream()
            .filter(transaction -> transaction.getType().equals(WalletTransactionType.HIRE))
            .map(walletTransactionMapper::toDto)
            .collect(Collectors.toSet());

        Set<WalletTransactionDTO> depositTrans = wallet
            .getTransactions()
            .stream()
            .filter(transaction -> transaction.getType().equals(WalletTransactionType.DEPOSIT))
            .map(walletTransactionMapper::toDto)
            .collect(Collectors.toSet());

        WalletHistoryDTO walletHistoryDTO = new WalletHistoryDTO();
        walletHistoryDTO.setAmount(wallet.getAmount());
        walletHistoryDTO.setHireTrans(hireTrans);
        walletHistoryDTO.setDepositTrans(depositTrans);

        return walletHistoryDTO;
    }
}
