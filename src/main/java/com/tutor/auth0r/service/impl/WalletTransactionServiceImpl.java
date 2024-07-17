package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.domain.enumeration.WalletTransactionStatus;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import com.tutor.auth0r.repository.WalletRepository;
import com.tutor.auth0r.repository.WalletTransactionRepository;
import com.tutor.auth0r.service.WalletService;
import com.tutor.auth0r.service.WalletTransactionService;
import com.tutor.auth0r.service.dto.CustomDTO.MonthlyRevenueDTO;
import com.tutor.auth0r.service.dto.CustomDTO.WithDrawLISTDTO;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import com.tutor.auth0r.service.mapper.WalletTransactionMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.tutor.auth0r.domain.WalletTransaction}.
 */
@Service
@Transactional
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private static final Logger log = LoggerFactory.getLogger(WalletTransactionServiceImpl.class);

    private final WalletTransactionRepository walletTransactionRepository;

    private final WalletTransactionMapper walletTransactionMapper;

    private final WalletService walletService;

    private final WalletRepository walletRepository;

    public WalletTransactionServiceImpl(
        WalletTransactionRepository walletTransactionRepository,
        WalletTransactionMapper walletTransactionMapper,
        WalletService walletService,
        WalletRepository walletRepository
    ) {
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletTransactionMapper = walletTransactionMapper;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletTransactionDTO save(WalletTransactionDTO walletTransactionDTO) {
        log.debug("Request to save WalletTransaction : {}", walletTransactionDTO);
        WalletTransaction walletTransaction = walletTransactionMapper.toEntity(walletTransactionDTO);
        walletTransaction = walletTransactionRepository.save(walletTransaction);
        return walletTransactionMapper.toDto(walletTransaction);
    }

    @Override
    public WalletTransactionDTO update(WalletTransactionDTO walletTransactionDTO) {
        log.debug("Request to update WalletTransaction : {}", walletTransactionDTO);
        WalletTransaction walletTransaction = walletTransactionMapper.toEntity(walletTransactionDTO);
        walletTransaction = walletTransactionRepository.save(walletTransaction);
        return walletTransactionMapper.toDto(walletTransaction);
    }

    @Override
    public Optional<WalletTransactionDTO> partialUpdate(WalletTransactionDTO walletTransactionDTO) {
        log.debug("Request to partially update WalletTransaction : {}", walletTransactionDTO);

        return walletTransactionRepository
            .findById(walletTransactionDTO.getId())
            .map(existingWalletTransaction -> {
                walletTransactionMapper.partialUpdate(existingWalletTransaction, walletTransactionDTO);

                return existingWalletTransaction;
            })
            .map(walletTransactionRepository::save)
            .map(walletTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletTransactionDTO> findAll() {
        log.debug("Request to get all WalletTransactions");
        return walletTransactionRepository
            .findAll()
            .stream()
            .map(walletTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletTransactionDTO> findOne(Long id) {
        log.debug("Request to get WalletTransaction : {}", id);
        return walletTransactionRepository.findById(id).map(walletTransactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WalletTransaction : {}", id);
        walletTransactionRepository.deleteById(id);
    }

    @Override
    public MonthlyRevenueDTO calculateMonthlyRevenueForAdmin(int year, int month) {
        LocalDate startDate = YearMonth.of(year, month).atDay(1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        Wallet wallet = walletService.getAdminWallet();

        List<WalletTransaction> transactions = walletTransactionRepository.findAllByWalletIdAndCreateAtBetween(
            wallet.getId(),
            startDate,
            endDate
        );

        Double totalRevenue = transactions.stream().mapToDouble(WalletTransaction::getAmount).sum();

        MonthlyRevenueDTO monthlyRevenueDTO = new MonthlyRevenueDTO();
        monthlyRevenueDTO.setTotalRevenue(totalRevenue);
        monthlyRevenueDTO.setTransactions(new HashSet<>(transactions));
        return monthlyRevenueDTO;
    }

    @Override
    public List<WalletTransaction> getWithdrawals() {
        return walletTransactionRepository.findByType(WalletTransactionType.WITHDRAWAL);
    }

    @Override
    public List<WithDrawLISTDTO> getAllWithdrawalDetails() {
        List<WalletTransaction> transactions = walletTransactionRepository.findAllWithdrawals();
        return transactions.stream().map(walletTransactionMapper::walletTransactionToWithDrawLISTDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public WithDrawLISTDTO updateTransactionStatus(Long transactionId) {
        WalletTransaction walletTransaction = walletTransactionRepository
            .findById(transactionId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + transactionId));

        if (walletTransaction.getType() != WalletTransactionType.WITHDRAWAL) {
            throw new IllegalArgumentException("Transaction type is not WITHDRAWAL, status update is not allowed");
        }

        if (walletTransaction.getStatus() != WalletTransactionStatus.VERIFING) {
            throw new IllegalArgumentException("Transaction Status is not VERIFING, updating is not allowed");
        }

        walletTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        walletTransaction = walletTransactionRepository.save(walletTransaction);

        return walletTransactionMapper.walletTransactionToWithDrawLISTDTO(walletTransaction);
    }

    @Transactional
    @Override
    public WithDrawLISTDTO rejectTransaction(Long transactionId) {
        WalletTransaction walletTransaction = walletTransactionRepository
            .findById(transactionId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + transactionId));

        if (
            walletTransaction.getType() != WalletTransactionType.WITHDRAWAL ||
            walletTransaction.getStatus() != WalletTransactionStatus.VERIFING
        ) {
            throw new IllegalArgumentException("Transaction type must be WITHDRAWAL and status must be VERIFING to reject the transaction");
        }

        WalletTransaction refund = new WalletTransaction();

        refund.setAmount(walletTransaction.getAmount());
        refund.setCreateAt(Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
        refund.setStatus(WalletTransactionStatus.SUCCEED);
        refund.setType(WalletTransactionType.REFUND);

        Wallet wallet = walletTransaction.getWallet();

        wallet.addTransactions(refund);
        walletRepository.save(wallet);

        // Update the transaction status to REJECTED
        walletTransaction.setStatus(WalletTransactionStatus.REJECTED);
        walletTransaction = walletTransactionRepository.save(walletTransaction);

        return walletTransactionMapper.walletTransactionToWithDrawLISTDTO(walletTransaction);
    }
}
