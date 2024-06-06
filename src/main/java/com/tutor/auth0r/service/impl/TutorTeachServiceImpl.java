package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.repository.TutorTeachRepository;
import com.tutor.auth0r.service.TutorTeachService;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import com.tutor.auth0r.service.mapper.TutorTeachMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.TutorTeach}.
 */
@Service
@Transactional
public class TutorTeachServiceImpl implements TutorTeachService {

    private final Logger log = LoggerFactory.getLogger(TutorTeachServiceImpl.class);

    private final TutorTeachRepository tutorTeachRepository;

    private final TutorTeachMapper tutorTeachMapper;

    public TutorTeachServiceImpl(TutorTeachRepository tutorTeachRepository, TutorTeachMapper tutorTeachMapper) {
        this.tutorTeachRepository = tutorTeachRepository;
        this.tutorTeachMapper = tutorTeachMapper;
    }

    @Override
    public TutorTeachDTO save(TutorTeachDTO tutorTeachDTO) {
        log.debug("Request to save TutorTeach : {}", tutorTeachDTO);
        TutorTeach tutorTeach = tutorTeachMapper.toEntity(tutorTeachDTO);
        tutorTeach = tutorTeachRepository.save(tutorTeach);
        return tutorTeachMapper.toDto(tutorTeach);
    }

    @Override
    public TutorTeachDTO update(TutorTeachDTO tutorTeachDTO) {
        log.debug("Request to update TutorTeach : {}", tutorTeachDTO);
        TutorTeach tutorTeach = tutorTeachMapper.toEntity(tutorTeachDTO);
        tutorTeach = tutorTeachRepository.save(tutorTeach);
        return tutorTeachMapper.toDto(tutorTeach);
    }

    @Override
    public Optional<TutorTeachDTO> partialUpdate(TutorTeachDTO tutorTeachDTO) {
        log.debug("Request to partially update TutorTeach : {}", tutorTeachDTO);

        return tutorTeachRepository
            .findById(tutorTeachDTO.getId())
            .map(existingTutorTeach -> {
                tutorTeachMapper.partialUpdate(existingTutorTeach, tutorTeachDTO);

                return existingTutorTeach;
            })
            .map(tutorTeachRepository::save)
            .map(tutorTeachMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorTeachDTO> findAll() {
        log.debug("Request to get all TutorTeaches");
        return tutorTeachRepository.findAll().stream().map(tutorTeachMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorTeachDTO> findOne(Long id) {
        log.debug("Request to get TutorTeach : {}", id);
        return tutorTeachRepository.findById(id).map(tutorTeachMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TutorTeach : {}", id);
        tutorTeachRepository.deleteById(id);
    }
}
