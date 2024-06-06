package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.repository.UserVerifyRepository;
import com.tutor.auth0r.service.UserVerifyService;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import com.tutor.auth0r.service.mapper.UserVerifyMapper;
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
 * Service Implementation for managing {@link com.tutor.auth0r.domain.UserVerify}.
 */
@Service
@Transactional
public class UserVerifyServiceImpl implements UserVerifyService {

    private final Logger log = LoggerFactory.getLogger(UserVerifyServiceImpl.class);

    private final UserVerifyRepository userVerifyRepository;

    private final UserVerifyMapper userVerifyMapper;

    public UserVerifyServiceImpl(UserVerifyRepository userVerifyRepository, UserVerifyMapper userVerifyMapper) {
        this.userVerifyRepository = userVerifyRepository;
        this.userVerifyMapper = userVerifyMapper;
    }

    @Override
    public UserVerifyDTO save(UserVerifyDTO userVerifyDTO) {
        log.debug("Request to save UserVerify : {}", userVerifyDTO);
        UserVerify userVerify = userVerifyMapper.toEntity(userVerifyDTO);
        userVerify = userVerifyRepository.save(userVerify);
        return userVerifyMapper.toDto(userVerify);
    }

    @Override
    public UserVerifyDTO update(UserVerifyDTO userVerifyDTO) {
        log.debug("Request to update UserVerify : {}", userVerifyDTO);
        UserVerify userVerify = userVerifyMapper.toEntity(userVerifyDTO);
        userVerify = userVerifyRepository.save(userVerify);
        return userVerifyMapper.toDto(userVerify);
    }

    @Override
    public Optional<UserVerifyDTO> partialUpdate(UserVerifyDTO userVerifyDTO) {
        log.debug("Request to partially update UserVerify : {}", userVerifyDTO);

        return userVerifyRepository
            .findById(userVerifyDTO.getId())
            .map(existingUserVerify -> {
                userVerifyMapper.partialUpdate(existingUserVerify, userVerifyDTO);

                return existingUserVerify;
            })
            .map(userVerifyRepository::save)
            .map(userVerifyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserVerifyDTO> findAll() {
        log.debug("Request to get all UserVerifies");
        return userVerifyRepository.findAll().stream().map(userVerifyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the userVerifies where AppUser is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserVerifyDTO> findAllWhereAppUserIsNull() {
        log.debug("Request to get all userVerifies where AppUser is null");
        return StreamSupport.stream(userVerifyRepository.findAll().spliterator(), false)
            .filter(userVerify -> userVerify.getAppUser() == null)
            .map(userVerifyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserVerifyDTO> findOne(Long id) {
        log.debug("Request to get UserVerify : {}", id);
        return userVerifyRepository.findById(id).map(userVerifyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserVerify : {}", id);
        userVerifyRepository.deleteById(id);
    }
}
