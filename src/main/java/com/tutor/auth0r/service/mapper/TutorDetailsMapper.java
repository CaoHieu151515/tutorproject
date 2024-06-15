package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorVideo;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TutorDetails} and its DTO {@link TutorDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorDetailsMapper extends EntityMapper<TutorDetailsDTO, TutorDetails> {
    @Mapping(target = "tutorVideo", source = "tutorVideo", qualifiedByName = "tutorVideoId")
    TutorDetailsDTO toDto(TutorDetails s);

    @Named("tutorVideoId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorVideoDTO toDtoTutorVideoId(TutorVideo tutorVideo);
}
