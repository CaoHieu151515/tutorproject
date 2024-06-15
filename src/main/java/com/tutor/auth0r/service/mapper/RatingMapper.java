package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.RatingDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring")
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId")
    RatingDTO toDto(Rating s);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDTO toDtoTutorId(Tutor tutor);
}
