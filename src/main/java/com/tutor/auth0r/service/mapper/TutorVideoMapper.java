package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.domain.TutorVideo;
import com.tutor.auth0r.service.dto.MediaDTO;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TutorVideo} and its DTO {@link TutorVideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorVideoMapper extends EntityMapper<TutorVideoDTO, TutorVideo> {
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId")
    TutorVideoDTO toDto(TutorVideo s);

    @Named("mediaId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    MediaDTO toDtoMediaId(Media media);
}
