package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.repository.RatingRepository;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.RatingService;
import com.tutor.auth0r.service.dto.RatingDTO;
import com.tutor.auth0r.service.mapper.RatingMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
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

    private final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    private final TutorRepository tutorRepository;

    private final RatingMapper ratingMapper;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingMapper ratingMapper, TutorRepository tutorRepository) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        log.debug("Request to save Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating = ratingRepository.save(rating);
        // Long tutor = rating.getTutor().getId();
        // if (tutor != null) {
        //     updateAverageRating(tutor);
        // }
        return ratingMapper.toDto(rating);
    }

    /////////////////// Thực hiện thử bằng domain ????
    // private void updateAverageRating(Long id) {
    //     Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutor not found"));
    //     Set<Rating> ratings = tutor.getRatings();

    //     if (ratings != null && !ratings.isEmpty()) {
    //         BigDecimal sumRatings = ratings
    //             .stream()
    //             .map(Rating::getRating)
    //             .map(BigDecimal::valueOf)
    //             .reduce(BigDecimal.ZERO, BigDecimal::add);
    //         log.debug("Request to save Rating : {}", sumRatings);

    //         int countRatings = ratings.size();
    //         BigDecimal count = BigDecimal.valueOf(countRatings);
    //         log.debug("Request to save Rating : {}", count);

    //         BigDecimal averageRating = sumRatings.divide(count, 2, RoundingMode.HALF_UP);
    //         tutor.setAverageRating(averageRating);
    //     } else {
    //         tutor.setAverageRating(BigDecimal.ZERO);
    //     }

    //     tutorRepository.save(tutor);
    // }

    @Override
    public RatingDTO update(RatingDTO ratingDTO) {
        log.debug("Request to update Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating = ratingRepository.save(rating);
        // Long tutor = rating.getTutor().getId();
        // if (tutor != null) {
        //     updateAverageRating(tutor);
        // }
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
     *  Get all the ratings where AppUser is {@code null}.
     *  @return the list of entities.
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
