package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Report;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.ReportDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId")
    ReportDTO toDto(Report s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDTO toDtoTutorId(Tutor tutor);
}
