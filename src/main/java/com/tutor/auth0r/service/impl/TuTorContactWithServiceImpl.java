package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.TuTorContactWith;
import com.tutor.auth0r.repository.TuTorContactWithRepository;
import com.tutor.auth0r.service.TuTorContactWithService;
import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import com.tutor.auth0r.service.mapper.TuTorContactWithMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.TuTorContactWith}.
 */
@Service
@Transactional
public class TuTorContactWithServiceImpl implements TuTorContactWithService {

    private static final Logger log = LoggerFactory.getLogger(TuTorContactWithServiceImpl.class);

    private final TuTorContactWithRepository tuTorContactWithRepository;

    private final TuTorContactWithMapper tuTorContactWithMapper;

    public TuTorContactWithServiceImpl(
        TuTorContactWithRepository tuTorContactWithRepository,
        TuTorContactWithMapper tuTorContactWithMapper
    ) {
        this.tuTorContactWithRepository = tuTorContactWithRepository;
        this.tuTorContactWithMapper = tuTorContactWithMapper;
    }

    @Override
    public TuTorContactWithDTO save(TuTorContactWithDTO tuTorContactWithDTO) {
        log.debug("Request to save TuTorContactWith : {}", tuTorContactWithDTO);
        TuTorContactWith tuTorContactWith = tuTorContactWithMapper.toEntity(tuTorContactWithDTO);
        tuTorContactWith = tuTorContactWithRepository.save(tuTorContactWith);
        return tuTorContactWithMapper.toDto(tuTorContactWith);
    }

    @Override
    public TuTorContactWithDTO update(TuTorContactWithDTO tuTorContactWithDTO) {
        log.debug("Request to update TuTorContactWith : {}", tuTorContactWithDTO);
        TuTorContactWith tuTorContactWith = tuTorContactWithMapper.toEntity(tuTorContactWithDTO);
        tuTorContactWith = tuTorContactWithRepository.save(tuTorContactWith);
        return tuTorContactWithMapper.toDto(tuTorContactWith);
    }

    @Override
    public Optional<TuTorContactWithDTO> partialUpdate(TuTorContactWithDTO tuTorContactWithDTO) {
        log.debug("Request to partially update TuTorContactWith : {}", tuTorContactWithDTO);

        return tuTorContactWithRepository
            .findById(tuTorContactWithDTO.getId())
            .map(existingTuTorContactWith -> {
                tuTorContactWithMapper.partialUpdate(existingTuTorContactWith, tuTorContactWithDTO);

                return existingTuTorContactWith;
            })
            .map(tuTorContactWithRepository::save)
            .map(tuTorContactWithMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TuTorContactWithDTO> findAll() {
        log.debug("Request to get all TuTorContactWiths");
        return tuTorContactWithRepository
            .findAll()
            .stream()
            .map(tuTorContactWithMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TuTorContactWithDTO> findOne(Long id) {
        log.debug("Request to get TuTorContactWith : {}", id);
        return tuTorContactWithRepository.findById(id).map(tuTorContactWithMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TuTorContactWith : {}", id);
        tuTorContactWithRepository.deleteById(id);
    }
}
