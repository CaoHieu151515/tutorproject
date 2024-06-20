package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.service.dto.RatingDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Tutor} and its DTO {@link TutorDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorMapper extends EntityMapper<TutorDTO, Tutor> {
    TutorMapper INSTANCE = Mappers.getMapper(TutorMapper.class);

    RatingMapper ratingins = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId")
    @Mapping(target = "rating", source = "ratings", qualifiedByName = "ratingUnestTuTor")
    TutorDTO toDto(Tutor s);

    @Named("tutorDetailsId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);

    @Named("ratingUnestTuTor")
    static RatingDTO ratingUnestTuTor(Rating rating) {
        if (rating == null) {
            return null;
        }
        return RatingMapper.INSTANCE.toDto(rating);
    }
}
