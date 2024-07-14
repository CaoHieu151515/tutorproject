package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.ThirdPartyTransaction;
import com.tutor.auth0r.repository.ThirdPartyTransactionRepository;
import com.tutor.auth0r.service.ThirdPartyTransactionService;
import com.tutor.auth0r.service.dto.ThirdPartyTransactionDTO;
import com.tutor.auth0r.service.mapper.ThirdPartyTransactionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.ThirdPartyTransaction}.
 */
@Service
@Transactional
public class ThirdPartyTransactionServiceImpl implements ThirdPartyTransactionService {

    private static final Logger log = LoggerFactory.getLogger(ThirdPartyTransactionServiceImpl.class);

    private final ThirdPartyTransactionRepository thirdPartyTransactionRepository;

    private final ThirdPartyTransactionMapper thirdPartyTransactionMapper;

    public ThirdPartyTransactionServiceImpl(
        ThirdPartyTransactionRepository thirdPartyTransactionRepository,
        ThirdPartyTransactionMapper thirdPartyTransactionMapper
    ) {
        this.thirdPartyTransactionRepository = thirdPartyTransactionRepository;
        this.thirdPartyTransactionMapper = thirdPartyTransactionMapper;
    }

    @Override
    public ThirdPartyTransactionDTO save(ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        log.debug("Request to save ThirdPartyTransaction : {}", thirdPartyTransactionDTO);
        ThirdPartyTransaction thirdPartyTransaction = thirdPartyTransactionMapper.toEntity(thirdPartyTransactionDTO);
        thirdPartyTransaction = thirdPartyTransactionRepository.save(thirdPartyTransaction);
        return thirdPartyTransactionMapper.toDto(thirdPartyTransaction);
    }

    @Override
    public ThirdPartyTransactionDTO update(ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        log.debug("Request to update ThirdPartyTransaction : {}", thirdPartyTransactionDTO);
        ThirdPartyTransaction thirdPartyTransaction = thirdPartyTransactionMapper.toEntity(thirdPartyTransactionDTO);
        thirdPartyTransaction = thirdPartyTransactionRepository.save(thirdPartyTransaction);
        return thirdPartyTransactionMapper.toDto(thirdPartyTransaction);
    }

    @Override
    public Optional<ThirdPartyTransactionDTO> partialUpdate(ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        log.debug("Request to partially update ThirdPartyTransaction : {}", thirdPartyTransactionDTO);

        return thirdPartyTransactionRepository
            .findById(thirdPartyTransactionDTO.getId())
            .map(existingThirdPartyTransaction -> {
                thirdPartyTransactionMapper.partialUpdate(existingThirdPartyTransaction, thirdPartyTransactionDTO);

                return existingThirdPartyTransaction;
            })
            .map(thirdPartyTransactionRepository::save)
            .map(thirdPartyTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThirdPartyTransactionDTO> findAll() {
        log.debug("Request to get all ThirdPartyTransactions");
        return thirdPartyTransactionRepository
            .findAll()
            .stream()
            .map(thirdPartyTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThirdPartyTransactionDTO> findOne(Long id) {
        log.debug("Request to get ThirdPartyTransaction : {}", id);
        return thirdPartyTransactionRepository.findById(id).map(thirdPartyTransactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThirdPartyTransaction : {}", id);
        thirdPartyTransactionRepository.deleteById(id);
    }
}
