package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tutor} and its DTO {@link TutorDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorMapper extends EntityMapper<TutorDTO, Tutor> {
    @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId")
    TutorDTO toDto(Tutor s);

    @Named("tutorDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);
}
