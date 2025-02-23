package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.config.PricingProperties;
import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.HireTutor;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.domain.enumeration.HireStatus;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import com.tutor.auth0r.domain.enumeration.WalletTransactionStatus;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import com.tutor.auth0r.repository.AppUserRepository;
import com.tutor.auth0r.repository.HireTutorRepository;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.AppUserService;
import com.tutor.auth0r.service.HireTutorService;
import com.tutor.auth0r.service.UserService;
import com.tutor.auth0r.service.WalletService;
import com.tutor.auth0r.service.dto.HireTutorDTO;
import com.tutor.auth0r.service.mapper.HireTutorMapper;
import com.tutor.auth0r.web.rest.errors.InvalidInputException;
import com.tutor.auth0r.web.rest.errors.NotEnoughMoneyException;
import com.tutor.auth0r.web.rest.errors.NotLoggedException;
import com.tutor.auth0r.web.rest.errors.UserAlreadyHiringException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.tutor.auth0r.domain.HireTutor}.
 */
@Service
@Transactional
public class HireTutorServiceImpl implements HireTutorService {

    private static final Logger log = LoggerFactory.getLogger(HireTutorServiceImpl.class);

    private final HireTutorRepository hireTutorRepository;

    private final HireTutorMapper hireTutorMapper;

    private final WalletService walletService;

    private final UserService userService;

    private final AppUserRepository appUserRepository;

    private final TutorRepository tutorRepository;

    private final AppUserService appUserService;

    public HireTutorServiceImpl(
        HireTutorRepository hireTutorRepository,
        HireTutorMapper hireTutorMapper,
        WalletService walletService,
        UserService userService,
        AppUserRepository appUserRepository,
        TutorRepository tutorRepository,
        AppUserService appUserService
    ) {
        this.hireTutorRepository = hireTutorRepository;
        this.hireTutorMapper = hireTutorMapper;
        this.walletService = walletService;
        this.userService = userService;
        this.appUserRepository = appUserRepository;
        this.tutorRepository = tutorRepository;
        this.appUserService = appUserService;
    }

    @Autowired
    private PricingProperties pricingProperties;

    @Override
    public HireTutorDTO save(HireTutorDTO hireTutorDTO) {
        log.debug("Request to save HireTutor : {}", hireTutorDTO);
        HireTutor hireTutor = hireTutorMapper.toEntity(hireTutorDTO);
        hireTutor = hireTutorRepository.save(hireTutor);
        return hireTutorMapper.toDto(hireTutor);
    }

    @Override
    public HireTutorDTO update(HireTutorDTO hireTutorDTO) {
        log.debug("Request to update HireTutor : {}", hireTutorDTO);
        HireTutor hireTutor = hireTutorMapper.toEntity(hireTutorDTO);
        hireTutor = hireTutorRepository.save(hireTutor);
        return hireTutorMapper.toDto(hireTutor);
    }

    @Override
    public Optional<HireTutorDTO> partialUpdate(HireTutorDTO hireTutorDTO) {
        log.debug("Request to partially update HireTutor : {}", hireTutorDTO);

        return hireTutorRepository
            .findById(hireTutorDTO.getId())
            .map(existingHireTutor -> {
                hireTutorMapper.partialUpdate(existingHireTutor, hireTutorDTO);

                return existingHireTutor;
            })
            .map(hireTutorRepository::save)
            .map(hireTutorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HireTutorDTO> findAll() {
        log.debug("Request to get all HireTutors");
        return hireTutorRepository.findAll().stream().map(hireTutorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HireTutorDTO> findOne(Long id) {
        log.debug("Request to get HireTutor : {}", id);
        return hireTutorRepository.findById(id).map(hireTutorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HireTutor : {}", id);
        hireTutorRepository.deleteById(id);
    }

    @Override
    public HireTutorDTO Hire(HireTutorDTO hireTutorDTO) {
        if (hireTutorDTO == null) {
            throw new InvalidInputException("Input data is null");
        }

        if (hireTutorRepository.existsByAppUserAndStatusDuring(hireTutorDTO.getAppUser().getId())) {
            throw new UserAlreadyHiringException("The user is already hiring a tutor with DURING status.");
        }

        log.debug("Request to save HireTutor : {}", hireTutorDTO);
        HireTutor hireTutor = hireTutorMapper.toEntity(hireTutorDTO);

        validateAndGetEntities(hireTutor);

        LocalDateTime startAtDateTime = LocalDateTime.now();
        LocalDateTime endAtDateTime = startAtDateTime.plusHours(hireTutorDTO.getTimeHire());

        // Chuyển đổi LocalDateTime thành LocalDate để lưu trữ
        // LocalDate startAt = startAtDateTime.toLocalDate();
        // LocalDate endAt = endAtDateTime.toLocalDate();

        hireTutor.setStatus(HireStatus.DURING);
        hireTutor.setTimeHire(hireTutorDTO.getTimeHire());
        hireTutor.setStartAt(startAtDateTime);
        hireTutor.setEndAt(endAtDateTime);
        hireTutor = hireTutorRepository.save(hireTutor);

        processWalletTransactions(hireTutor);

        return hireTutorMapper.toDto(hireTutor);
    }

    private void validateAndGetEntities(HireTutor hireTutor) {
        AppUser hirer = appUserRepository
            .findById(hireTutor.getAppUser().getId())
            .orElseThrow(() -> new RuntimeException("AppUser not found"));

        Tutor tutor = tutorRepository.findById(hireTutor.getTutor().getId()).orElseThrow(() -> new RuntimeException("Tutor not found"));

        if (tutor.getStatus().equals(TuStatus.BUSY)) {
            throw new UserAlreadyHiringException("The Tutor is already hiring a tutor with DURING status.");
        }

        tutor.setStatus(TuStatus.BUSY);
        tutorRepository.save(tutor);
        hireTutor.setAppUser(hirer);
        hireTutor.setTutor(tutor);

        if (!userService.getUserWithAuthorities().isPresent()) {
            throw new NotLoggedException();
        }
    }

    private void processWalletTransactions(HireTutor hireTutor) {
        Double trueAmount = hireTutor.getTutor().getPrice() * hireTutor.getTimeHire();

        log.debug("Request to update HireTutor : {}", trueAmount);

        Wallet adminWallet = walletService.getAdminWallet();
        Wallet tutorWallet = walletService.getWalletByUserLogin(hireTutor.getTutor().getAppUser().getUser().getLogin());
        Wallet hirerWallet = walletService.getWalletByUserLogin(hireTutor.getAppUser().getUser().getLogin());

        if (hirerWallet.getAmount() < trueAmount) {
            throw new NotEnoughMoneyException();
        }

        Double serviceFee = trueAmount * pricingProperties.getFreePercentage();
        log.debug("Request to update HireTutor : {}", serviceFee);

        Double tutorGain = trueAmount * pricingProperties.getfreePercentageHireGain();
        log.debug("Request to update HireTutor : {}", tutorGain);

        addTransactionToWallet(hirerWallet, trueAmount, WalletTransactionType.HIRE, hireTutor);
        addTransactionToWallet(tutorWallet, tutorGain, WalletTransactionType.TUTORGAIN, hireTutor);
        addTransactionToWallet(adminWallet, serviceFee, WalletTransactionType.SERVICE_FEE_EARN, hireTutor);

        walletService.save(adminWallet);
        walletService.save(tutorWallet);
        walletService.save(hirerWallet);
    }

    private void addTransactionToWallet(Wallet wallet, Double amount, WalletTransactionType type, HireTutor hireTutor) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setStatus(WalletTransactionStatus.SUCCEED);
        transaction.setCreateAt(Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
        transaction.setHireTutor(hireTutor);
        wallet.addTransactions(transaction);
    }

    @Override
    public HireTutorDTO updatesTatus(Long id) {
        log.debug("Request to update HireTutor : {}", id);
        HireTutor hireTutor = hireTutorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutor not found"));

        Tutor tutor = tutorRepository.findById(hireTutor.getTutor().getId()).orElseThrow(() -> new RuntimeException("Tutor not found"));

        tutor.setStatus(TuStatus.READY);
        tutorRepository.save(tutor);

        hireTutor.setStatus(HireStatus.DONE);
        hireTutor = hireTutorRepository.save(hireTutor);
        return hireTutorMapper.toDto(hireTutor);
    }

    @Override
    public HireTutorDTO updatesTatusCancel(Long id) {
        log.debug("Request to update HireTutor : {}", id);
        HireTutor hireTutor = hireTutorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutor not found"));

        if (hireTutor.getStatus() != HireStatus.DURING) {
            throw new RuntimeException("Status not During");
        }

        Tutor tutor = tutorRepository.findById(hireTutor.getTutor().getId()).orElseThrow(() -> new RuntimeException("Tutor not found"));

        tutor.setStatus(TuStatus.READY);
        tutorRepository.save(tutor);

        hireTutor.setStatus(HireStatus.CANCEL);
        hireTutor = hireTutorRepository.save(hireTutor);
        return hireTutorMapper.toDto(hireTutor);
    }
    // @Override
    // public HireTutorDTO Hire(HireTutorDTO hireTutorDTO) {
    // log.debug("Request to save HireTutor : {}", hireTutorDTO);
    // HireTutor hireTutor = hireTutorMapper.toEntity(hireTutorDTO);

    // Optional<AppUser> hirerOptionall =
    // appUserRepository.findById(hireTutor.getAppUser().getId());
    // AppUser hirer = hirerOptionall.orElseThrow(() -> new
    // RuntimeException("AppUser not found"));

    // Optional<Tutor> tutorOptionall =
    // tutorRepository.findById(hireTutor.getTutor().getId());
    // Tutor tutor = tutorOptionall.orElseThrow(() -> new RuntimeException("tutor
    // not found"));

    // hireTutor.setAppUser(hirer);
    // hireTutor.setTutor(tutor);

    // Double TrueAmount = tutor.getPrice()*hireTutor.getTimeHire();

    // Optional<User> hirerOptional = userService.getUserWithAuthorities();

    // Wallet adminWallet = walletService.getAdminWallet();
    // Wallet tutorWallet =
    // walletService.getWalletByUserLogin(hireTutor.getAppUser().getUser().getLogin());
    // Wallet hirerWallet =
    // walletService.getWalletByUserLogin(hireTutor.getTutor().getAppUser().getUser().getLogin());

    // Double serviceFee = TrueAmount * pricingProperties.getFreePercentage();

    // Double tuTorGain = TrueAmount *
    // pricingProperties.getfreePercentageHireGain();

    // if (!hirerOptional.isPresent()) {
    // throw new NotLoggedException();
    // }

    // if (hirerWallet.getAmount() < TrueAmount) {
    // throw new NotEnoughMoneyException();
    // }

    // WalletTransaction hirerWalletTransaction = new WalletTransaction();
    // hirerWalletTransaction.setAmount(TrueAmount);
    // hirerWalletTransaction.setType(WalletTransactionType.HIRE);
    // hirerWalletTransaction.setStatus(WalletTransactionStatus.SUCCEED);
    // hirerWallet.addTransactions(hirerWalletTransaction);

    // WalletTransaction tutorWalletTransaction = new WalletTransaction();
    // tutorWalletTransaction.setAmount(tuTorGain);
    // tutorWalletTransaction.setType(WalletTransactionType.TUTORGAIN);
    // tutorWalletTransaction.setStatus(WalletTransactionStatus.SUCCEED);
    // tutorWallet.addTransactions(tutorWalletTransaction);

    // WalletTransaction adminWalletTransaction = new WalletTransaction();
    // adminWalletTransaction.setAmount(serviceFee);
    // adminWalletTransaction.setType(WalletTransactionType.SERVICE_FEE_EARN);
    // adminWalletTransaction.setStatus(WalletTransactionStatus.SUCCEED);
    // adminWallet.addTransactions(adminWalletTransaction);

    // walletService.save(adminWallet);
    // walletService.save(tutorWallet);
    // walletService.save(hirerWallet);

    // hireTutor.setStatus(HireStatus.DURING);

    // hireTutor = hireTutorRepository.save(hireTutor);

    // return hireTutorMapper.toDto(hireTutor);
    // }
}
