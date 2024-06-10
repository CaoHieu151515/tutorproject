package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.repository.IdentityCardRepository;
import com.tutor.auth0r.service.IdentityCardService;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.mapper.IdentityCardMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.IdentityCard}.
 */
@Service
@Transactional
public class IdentityCardServiceImpl implements IdentityCardService {

    private final Logger log = LoggerFactory.getLogger(IdentityCardServiceImpl.class);

    private final IdentityCardRepository identityCardRepository;

    private final IdentityCardMapper identityCardMapper;

    public IdentityCardServiceImpl(IdentityCardRepository identityCardRepository, IdentityCardMapper identityCardMapper) {
        this.identityCardRepository = identityCardRepository;
        this.identityCardMapper = identityCardMapper;
    }

    @Override
    public IdentityCardDTO save(IdentityCardDTO identityCardDTO) {
        log.debug("Request to save IdentityCard : {}", identityCardDTO);
        IdentityCard identityCard = identityCardMapper.toEntity(identityCardDTO);
        identityCard = identityCardRepository.save(identityCard);
        return identityCardMapper.toDto(identityCard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IdentityCardDTO> findAll() {
        log.debug("Request to get all IdentityCards");
        return identityCardRepository.findAll().stream().map(identityCardMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the identityCards where UserVerify is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IdentityCardDTO> findAllWhereUserVerifyIsNull() {
        log.debug("Request to get all identityCards where UserVerify is null");
        return StreamSupport.stream(identityCardRepository.findAll().spliterator(), false)
            .filter(identityCard -> identityCard.getUserVerify() == null)
            .map(identityCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IdentityCardDTO> findOne(Long id) {
        log.debug("Request to get IdentityCard : {}", id);
        return identityCardRepository.findById(id).map(identityCardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdentityCard : {}", id);
        identityCardRepository.deleteById(id);
    }

    @Override
    public IdentityCardDTO update(IdentityCardDTO identityCardDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<IdentityCardDTO> partialUpdate(IdentityCardDTO identityCardDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partialUpdate'");
    }
}
