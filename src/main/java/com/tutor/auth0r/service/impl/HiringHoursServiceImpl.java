package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.HiringHours;
import com.tutor.auth0r.repository.HiringHoursRepository;
import com.tutor.auth0r.service.HiringHoursService;
import com.tutor.auth0r.service.dto.HiringHoursDTO;
import com.tutor.auth0r.service.mapper.HiringHoursMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.HiringHours}.
 */
@Service
@Transactional
public class HiringHoursServiceImpl implements HiringHoursService {

    private final Logger log = LoggerFactory.getLogger(HiringHoursServiceImpl.class);

    private final HiringHoursRepository hiringHoursRepository;

    private final HiringHoursMapper hiringHoursMapper;

    public HiringHoursServiceImpl(HiringHoursRepository hiringHoursRepository, HiringHoursMapper hiringHoursMapper) {
        this.hiringHoursRepository = hiringHoursRepository;
        this.hiringHoursMapper = hiringHoursMapper;
    }

    @Override
    public HiringHoursDTO save(HiringHoursDTO hiringHoursDTO) {
        log.debug("Request to save HiringHours : {}", hiringHoursDTO);
        HiringHours hiringHours = hiringHoursMapper.toEntity(hiringHoursDTO);
        hiringHours = hiringHoursRepository.save(hiringHours);
        return hiringHoursMapper.toDto(hiringHours);
    }

    @Override
    public HiringHoursDTO update(HiringHoursDTO hiringHoursDTO) {
        log.debug("Request to update HiringHours : {}", hiringHoursDTO);
        HiringHours hiringHours = hiringHoursMapper.toEntity(hiringHoursDTO);
        hiringHours = hiringHoursRepository.save(hiringHours);
        return hiringHoursMapper.toDto(hiringHours);
    }

    @Override
    public Optional<HiringHoursDTO> partialUpdate(HiringHoursDTO hiringHoursDTO) {
        log.debug("Request to partially update HiringHours : {}", hiringHoursDTO);

        return hiringHoursRepository
            .findById(hiringHoursDTO.getId())
            .map(existingHiringHours -> {
                hiringHoursMapper.partialUpdate(existingHiringHours, hiringHoursDTO);

                return existingHiringHours;
            })
            .map(hiringHoursRepository::save)
            .map(hiringHoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HiringHoursDTO> findAll() {
        log.debug("Request to get all HiringHours");
        return hiringHoursRepository.findAll().stream().map(hiringHoursMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HiringHoursDTO> findOne(Long id) {
        log.debug("Request to get HiringHours : {}", id);
        return hiringHoursRepository.findById(id).map(hiringHoursMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HiringHours : {}", id);
        hiringHoursRepository.deleteById(id);
    }
}
