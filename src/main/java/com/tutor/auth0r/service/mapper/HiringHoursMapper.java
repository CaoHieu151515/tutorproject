package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.HiringHours;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.HiringHoursDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HiringHours} and its DTO {@link HiringHoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface HiringHoursMapper extends EntityMapper<HiringHoursDTO, HiringHours> {
    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId")
    HiringHoursDTO toDto(HiringHours s);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorDTO toDtoTutorId(Tutor tutor);
}
