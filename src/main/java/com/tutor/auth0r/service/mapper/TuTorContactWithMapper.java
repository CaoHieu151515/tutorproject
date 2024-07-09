package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.TuTorContactWith;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link TuTorContactWith} and its DTO {@link TuTorContactWithDTO}.
 */
@Mapper(componentModel = "spring")
public interface TuTorContactWithMapper extends EntityMapper<TuTorContactWithDTO, TuTorContactWith> {
    TuTorContactWithMapper INSTANCE = Mappers.getMapper(TuTorContactWithMapper.class);

    @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId")
    TuTorContactWithDTO toDto(TuTorContactWith s);

    @Named("tutorDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);
}
