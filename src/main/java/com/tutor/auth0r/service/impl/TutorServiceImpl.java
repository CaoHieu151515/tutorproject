package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.TutorService;
import com.tutor.auth0r.service.dto.TuTorCusTomDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.mapper.CustomTutorMapper;
import com.tutor.auth0r.service.mapper.TutorMapper;
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
 * Service Implementation for managing {@link com.tutor.auth0r.domain.Tutor}.
 */
@Service
@Transactional
public class TutorServiceImpl implements TutorService {

    private final Logger log = LoggerFactory.getLogger(TutorServiceImpl.class);

    private final TutorRepository tutorRepository;

    private final TutorMapper tutorMapper;

    private final CustomTutorMapper customTutorMapper;

    public TutorServiceImpl(TutorRepository tutorRepository, TutorMapper tutorMapper, CustomTutorMapper customTutorMapper) {
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
        this.customTutorMapper = customTutorMapper;
    }

    @Override
    public TutorDTO save(TutorDTO tutorDTO) {
        log.debug("Request to save Tutor : {}", tutorDTO);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        tutor = tutorRepository.save(tutor);
        return tutorMapper.toDto(tutor);
    }

    @Override
    public TutorDTO update(TutorDTO tutorDTO) {
        log.debug("Request to update Tutor : {}", tutorDTO);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        tutor = tutorRepository.save(tutor);
        return tutorMapper.toDto(tutor);
    }

    @Override
    public Optional<TutorDTO> partialUpdate(TutorDTO tutorDTO) {
        log.debug("Request to partially update Tutor : {}", tutorDTO);

        return tutorRepository
            .findById(tutorDTO.getId())
            .map(existingTutor -> {
                tutorMapper.partialUpdate(existingTutor, tutorDTO);

                return existingTutor;
            })
            .map(tutorRepository::save)
            .map(tutorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorDTO> findAll() {
        log.debug("Request to get all Tutors");
        return tutorRepository.findAll().stream().map(tutorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the tutors where AppUser is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TutorDTO> findAllWhereAppUserIsNull() {
        log.debug("Request to get all tutors where AppUser is null");
        return StreamSupport.stream(tutorRepository.findAll().spliterator(), false)
            .filter(tutor -> tutor.getAppUser() == null)
            .map(tutorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorDTO> findOne(Long id) {
        log.debug("Request to get Tutor : {}", id);
        return tutorRepository.findById(id).map(tutorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tutor : {}", id);
        tutorRepository.deleteById(id);
    }

    @Override
    public Tutor save(Tutor tutor) {
        log.debug("Request to save Wallet : {}", tutor);
        tutor = tutorRepository.save(tutor);
        return tutor;
    }

    @Transactional
    @Override
    public Optional<TuTorCusTomDTO> findOneCustom(Long id) {
        return tutorRepository.findById(id).map(customTutorMapper::toDto);
    }
}
