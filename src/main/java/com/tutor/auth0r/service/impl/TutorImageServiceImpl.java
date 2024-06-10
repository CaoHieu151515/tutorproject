package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.TutorImage;
import com.tutor.auth0r.repository.TutorImageRepository;
import com.tutor.auth0r.service.TutorImageService;
import com.tutor.auth0r.service.dto.TutorImageDTO;
import com.tutor.auth0r.service.mapper.TutorImageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.TutorImage}.
 */
@Service
@Transactional
public class TutorImageServiceImpl implements TutorImageService {

    private final Logger log = LoggerFactory.getLogger(TutorImageServiceImpl.class);

    private final TutorImageRepository tutorImageRepository;

    private final TutorImageMapper tutorImageMapper;

    public TutorImageServiceImpl(TutorImageRepository tutorImageRepository, TutorImageMapper tutorImageMapper) {
        this.tutorImageRepository = tutorImageRepository;
        this.tutorImageMapper = tutorImageMapper;
    }

    @Override
    public TutorImageDTO save(TutorImageDTO tutorImageDTO) {
        log.debug("Request to save TutorImage : {}", tutorImageDTO);
        TutorImage tutorImage = tutorImageMapper.toEntity(tutorImageDTO);
        tutorImage = tutorImageRepository.save(tutorImage);
        return tutorImageMapper.toDto(tutorImage);
    }

    @Override
    public TutorImageDTO update(TutorImageDTO tutorImageDTO) {
        log.debug("Request to update TutorImage : {}", tutorImageDTO);
        TutorImage tutorImage = tutorImageMapper.toEntity(tutorImageDTO);
        tutorImage = tutorImageRepository.save(tutorImage);
        return tutorImageMapper.toDto(tutorImage);
    }

    @Override
    public Optional<TutorImageDTO> partialUpdate(TutorImageDTO tutorImageDTO) {
        log.debug("Request to partially update TutorImage : {}", tutorImageDTO);

        return tutorImageRepository
            .findById(tutorImageDTO.getId())
            .map(existingTutorImage -> {
                tutorImageMapper.partialUpdate(existingTutorImage, tutorImageDTO);

                return existingTutorImage;
            })
            .map(tutorImageRepository::save)
            .map(tutorImageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorImageDTO> findAll() {
        log.debug("Request to get all TutorImages");
        return tutorImageRepository.findAll().stream().map(tutorImageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorImageDTO> findOne(Long id) {
        log.debug("Request to get TutorImage : {}", id);
        return tutorImageRepository.findById(id).map(tutorImageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TutorImage : {}", id);
        tutorImageRepository.deleteById(id);
    }
}
