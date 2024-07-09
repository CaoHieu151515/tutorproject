package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.repository.TutorDetailsRepository;
import com.tutor.auth0r.service.TutorDetailsService;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.mapper.TutorDetailsMapper;
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
 * Service Implementation for managing {@link com.tutor.auth0r.domain.TutorDetails}.
 */
@Service
@Transactional
public class TutorDetailsServiceImpl implements TutorDetailsService {

    private static final Logger log = LoggerFactory.getLogger(TutorDetailsServiceImpl.class);

    private final TutorDetailsRepository tutorDetailsRepository;

    private final TutorDetailsMapper tutorDetailsMapper;

    public TutorDetailsServiceImpl(TutorDetailsRepository tutorDetailsRepository, TutorDetailsMapper tutorDetailsMapper) {
        this.tutorDetailsRepository = tutorDetailsRepository;
        this.tutorDetailsMapper = tutorDetailsMapper;
    }

    @Override
    public TutorDetailsDTO save(TutorDetailsDTO tutorDetailsDTO) {
        log.debug("Request to save TutorDetails : {}", tutorDetailsDTO);
        TutorDetails tutorDetails = tutorDetailsMapper.toEntity(tutorDetailsDTO);
        tutorDetails = tutorDetailsRepository.save(tutorDetails);
        return tutorDetailsMapper.toDto(tutorDetails);
    }

    @Override
    public TutorDetailsDTO update(TutorDetailsDTO tutorDetailsDTO) {
        log.debug("Request to update TutorDetails : {}", tutorDetailsDTO);
        TutorDetails tutorDetails = tutorDetailsMapper.toEntity(tutorDetailsDTO);
        tutorDetails = tutorDetailsRepository.save(tutorDetails);
        return tutorDetailsMapper.toDto(tutorDetails);
    }

    @Override
    public Optional<TutorDetailsDTO> partialUpdate(TutorDetailsDTO tutorDetailsDTO) {
        log.debug("Request to partially update TutorDetails : {}", tutorDetailsDTO);

        return tutorDetailsRepository
            .findById(tutorDetailsDTO.getId())
            .map(existingTutorDetails -> {
                tutorDetailsMapper.partialUpdate(existingTutorDetails, tutorDetailsDTO);

                return existingTutorDetails;
            })
            .map(tutorDetailsRepository::save)
            .map(tutorDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorDetailsDTO> findAll() {
        log.debug("Request to get all TutorDetails");
        return tutorDetailsRepository.findAll().stream().map(tutorDetailsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the tutorDetails where Tutor is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TutorDetailsDTO> findAllWhereTutorIsNull() {
        log.debug("Request to get all tutorDetails where Tutor is null");
        return StreamSupport.stream(tutorDetailsRepository.findAll().spliterator(), false)
            .filter(tutorDetails -> tutorDetails.getTutor() == null)
            .map(tutorDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorDetailsDTO> findOne(Long id) {
        log.debug("Request to get TutorDetails : {}", id);
        return tutorDetailsRepository.findById(id).map(tutorDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TutorDetails : {}", id);
        tutorDetailsRepository.deleteById(id);
    }
}
