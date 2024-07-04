package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorVideo;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link TutorDetails} and its DTO {@link TutorDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorDetailsMapper extends EntityMapper<TutorDetailsDTO, TutorDetails> {
    TutorDetailsMapper INSTANCE = Mappers.getMapper(TutorDetailsMapper.class);
    TutorVideoMapper videoINS = Mappers.getMapper(TutorVideoMapper.class);

    @Mapping(target = "tutorVideo", source = "tutorVideo", qualifiedByName = "videomapper")
    TutorDetailsDTO toDto(TutorDetails s);

    @Named("tutorVideoId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorVideoDTO toDtoTutorVideoId(TutorVideo tutorVideo);

    @Named("videomapper")
    static TutorVideoDTO videomapper(TutorVideo tutorVideo) {
        if (tutorVideo == null) {
            return null;
        }
        return TutorVideoMapper.INSTANCE.toDto(tutorVideo);
    }
}
