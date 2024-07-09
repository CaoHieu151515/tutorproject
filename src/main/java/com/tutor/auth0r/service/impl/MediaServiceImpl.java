package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.repository.MediaRepository;
import com.tutor.auth0r.service.MediaService;
import com.tutor.auth0r.service.dto.MediaDTO;
import com.tutor.auth0r.service.mapper.MediaMapper;
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
 * Service Implementation for managing {@link com.tutor.auth0r.domain.Media}.
 */
@Service
@Transactional
public class MediaServiceImpl implements MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public MediaDTO save(MediaDTO mediaDTO) {
        log.debug("Request to save Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        return mediaMapper.toDto(media);
    }

    @Override
    public MediaDTO update(MediaDTO mediaDTO) {
        log.debug("Request to update Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        return mediaMapper.toDto(media);
    }

    @Override
    public Optional<MediaDTO> partialUpdate(MediaDTO mediaDTO) {
        log.debug("Request to partially update Media : {}", mediaDTO);

        return mediaRepository
            .findById(mediaDTO.getId())
            .map(existingMedia -> {
                mediaMapper.partialUpdate(existingMedia, mediaDTO);

                return existingMedia;
            })
            .map(mediaRepository::save)
            .map(mediaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaDTO> findAll() {
        log.debug("Request to get all Media");
        return mediaRepository.findAll().stream().map(mediaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the media where AcademicRank is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MediaDTO> findAllWhereAcademicRankIsNull() {
        log.debug("Request to get all media where AcademicRank is null");
        return StreamSupport.stream(mediaRepository.findAll().spliterator(), false)
            .filter(media -> media.getAcademicRank() == null)
            .map(mediaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the media where TutorImage is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MediaDTO> findAllWhereTutorImageIsNull() {
        log.debug("Request to get all media where TutorImage is null");
        return StreamSupport.stream(mediaRepository.findAll().spliterator(), false)
            .filter(media -> media.getTutorImage() == null)
            .map(mediaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the media where TutorVideo is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MediaDTO> findAllWhereTutorVideoIsNull() {
        log.debug("Request to get all media where TutorVideo is null");
        return StreamSupport.stream(mediaRepository.findAll().spliterator(), false)
            .filter(media -> media.getTutorVideo() == null)
            .map(mediaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MediaDTO> findOne(Long id) {
        log.debug("Request to get Media : {}", id);
        return mediaRepository.findById(id).map(mediaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Media : {}", id);
        mediaRepository.deleteById(id);
    }
}
