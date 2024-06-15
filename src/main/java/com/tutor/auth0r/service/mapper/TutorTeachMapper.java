package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TutorTeach} and its DTO {@link TutorTeachDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorTeachMapper extends EntityMapper<TutorTeachDTO, TutorTeach> {
    @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId")
    TutorTeachDTO toDto(TutorTeach s);

    @Named("tutorDetailsId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);
}
