package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link TutorTeach} and its DTO {@link TutorTeachDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorTeachMapper extends EntityMapper<TutorTeachDTO, TutorTeach> {
    TutorTeachMapper INSTANCE = Mappers.getMapper(TutorTeachMapper.class);

    // @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId")
    @Mapping(target = "tutorDetails", ignore = true)
    TutorTeachDTO toDto(TutorTeach s);

    @Named("tutorDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);
}
