package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.repository.AppUserRepository;
import com.tutor.auth0r.repository.RatingRepository;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.RatingService;
import com.tutor.auth0r.service.TutorService;
import com.tutor.auth0r.service.dto.RatingDTO;
import com.tutor.auth0r.service.mapper.RatingMapper;
import java.time.Instant;
import java.time.ZoneId;
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
 * Service Implementation for managing {@link com.tutor.auth0r.domain.Rating}.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private static final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    private final TutorRepository tutorRepository;

    private final TutorService tutorService;

    private final RatingMapper ratingMapper;

    private final AppUserRepository appUserRepository;

    public RatingServiceImpl(
        RatingRepository ratingRepository,
        RatingMapper ratingMapper,
        TutorRepository tutorRepository,
        TutorService tutorService,
        AppUserRepository appUserRepository
    ) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
        this.tutorRepository = tutorRepository;
        this.tutorService = tutorService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        log.debug("Request to save Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);

        rating.setDate(Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
        Tutor tutor = tutorRepository.findById(rating.getTutor().getId()).orElseThrow(() -> new RuntimeException("Tutor not found"));
        AppUser appUser = appUserRepository
            .findById(rating.getAppUser().getId())
            .orElseThrow(() -> new RuntimeException("Tutor not found"));
        rating.setTutor(tutor);
        rating.setAppUser(appUser);
        rating = ratingRepository.save(rating);

        tutor.addRating(rating);
        tutor.updateAverageRating();

        tutorRepository.save(tutor);

        return ratingMapper.toDto(rating);
    }

    @Override
    public RatingDTO update(RatingDTO ratingDTO) {
        log.debug("Request to update Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);

        // rating = ratingRepository.save(rating);

        Tutor tutor = tutorRepository.findById(rating.getTutor().getId()).orElseThrow(() -> new RuntimeException("Tutor not found"));
        tutor.addRating(rating);
        tutor.updateAverageRating();
        tutorService.save(tutor);

        return ratingMapper.toDto(rating);
    }

    @Override
    public Optional<RatingDTO> partialUpdate(RatingDTO ratingDTO) {
        log.debug("Request to partially update Rating : {}", ratingDTO);

        return ratingRepository
            .findById(ratingDTO.getId())
            .map(existingRating -> {
                ratingMapper.partialUpdate(existingRating, ratingDTO);

                return existingRating;
            })
            .map(ratingRepository::save)
            .map(ratingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> findAll() {
        log.debug("Request to get all Ratings");
        return ratingRepository.findAll().stream().map(ratingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the ratings where AppUser is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RatingDTO> findAllWhereAppUserIsNull() {
        log.debug("Request to get all ratings where AppUser is null");
        return StreamSupport.stream(ratingRepository.findAll().spliterator(), false)
            .filter(rating -> rating.getAppUser() == null)
            .map(ratingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RatingDTO> findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        return ratingRepository.findById(id).map(ratingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
    }
}
