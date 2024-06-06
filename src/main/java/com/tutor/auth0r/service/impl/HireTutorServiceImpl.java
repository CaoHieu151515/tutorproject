package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.HireTutor;
import com.tutor.auth0r.repository.HireTutorRepository;
import com.tutor.auth0r.service.HireTutorService;
import com.tutor.auth0r.service.dto.HireTutorDTO;
import com.tutor.auth0r.service.mapper.HireTutorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.HireTutor}.
 */
@Service
@Transactional
public class HireTutorServiceImpl implements HireTutorService {

    private final Logger log = LoggerFactory.getLogger(HireTutorServiceImpl.class);

    private final HireTutorRepository hireTutorRepository;

    private final HireTutorMapper hireTutorMapper;

    public HireTutorServiceImpl(HireTutorRepository hireTutorRepository, HireTutorMapper hireTutorMapper) {
        this.hireTutorRepository = hireTutorRepository;
        this.hireTutorMapper = hireTutorMapper;
    }

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
}
