package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.TutorVideo;
import com.tutor.auth0r.repository.TutorVideoRepository;
import com.tutor.auth0r.service.TutorVideoService;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
import com.tutor.auth0r.service.mapper.TutorVideoMapper;
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
 * Service Implementation for managing {@link com.tutor.auth0r.domain.TutorVideo}.
 */
@Service
@Transactional
public class TutorVideoServiceImpl implements TutorVideoService {

    private final Logger log = LoggerFactory.getLogger(TutorVideoServiceImpl.class);

    private final TutorVideoRepository tutorVideoRepository;

    private final TutorVideoMapper tutorVideoMapper;

    public TutorVideoServiceImpl(TutorVideoRepository tutorVideoRepository, TutorVideoMapper tutorVideoMapper) {
        this.tutorVideoRepository = tutorVideoRepository;
        this.tutorVideoMapper = tutorVideoMapper;
    }

    @Override
    public TutorVideoDTO save(TutorVideoDTO tutorVideoDTO) {
        log.debug("Request to save TutorVideo : {}", tutorVideoDTO);
        TutorVideo tutorVideo = tutorVideoMapper.toEntity(tutorVideoDTO);
        tutorVideo = tutorVideoRepository.save(tutorVideo);
        return tutorVideoMapper.toDto(tutorVideo);
    }

    @Override
    public TutorVideoDTO update(TutorVideoDTO tutorVideoDTO) {
        log.debug("Request to update TutorVideo : {}", tutorVideoDTO);
        TutorVideo tutorVideo = tutorVideoMapper.toEntity(tutorVideoDTO);
        tutorVideo = tutorVideoRepository.save(tutorVideo);
        return tutorVideoMapper.toDto(tutorVideo);
    }

    @Override
    public Optional<TutorVideoDTO> partialUpdate(TutorVideoDTO tutorVideoDTO) {
        log.debug("Request to partially update TutorVideo : {}", tutorVideoDTO);

        return tutorVideoRepository
            .findById(tutorVideoDTO.getId())
            .map(existingTutorVideo -> {
                tutorVideoMapper.partialUpdate(existingTutorVideo, tutorVideoDTO);

                return existingTutorVideo;
            })
            .map(tutorVideoRepository::save)
            .map(tutorVideoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorVideoDTO> findAll() {
        log.debug("Request to get all TutorVideos");
        return tutorVideoRepository.findAll().stream().map(tutorVideoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the tutorVideos where TutorDetails is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TutorVideoDTO> findAllWhereTutorDetailsIsNull() {
        log.debug("Request to get all tutorVideos where TutorDetails is null");
        return StreamSupport.stream(tutorVideoRepository.findAll().spliterator(), false)
            .filter(tutorVideo -> tutorVideo.getTutorDetails() == null)
            .map(tutorVideoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorVideoDTO> findOne(Long id) {
        log.debug("Request to get TutorVideo : {}", id);
        return tutorVideoRepository.findById(id).map(tutorVideoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TutorVideo : {}", id);
        tutorVideoRepository.deleteById(id);
    }
}
