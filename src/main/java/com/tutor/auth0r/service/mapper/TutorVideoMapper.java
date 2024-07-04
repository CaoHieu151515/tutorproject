package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.domain.TutorVideo;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.service.dto.MediaDTO;
import com.tutor.auth0r.service.dto.TutorVideoDTO;
import com.tutor.auth0r.service.dto.WalletDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link TutorVideo} and its DTO {@link TutorVideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorVideoMapper extends EntityMapper<TutorVideoDTO, TutorVideo> {
    TutorVideoMapper INSTANCE = Mappers.getMapper(TutorVideoMapper.class);
    MediaMapper mediaIns = Mappers.getMapper(MediaMapper.class);

    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId2")
    TutorVideoDTO toDto(TutorVideo s);

    @Named("mediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MediaDTO toDtoMediaId(Media media);

    @Named("mediaId2")
    static MediaDTO mediamapper(Media media) {
        if (media == null) {
            return null;
        }
        return MediaMapper.INSTANCE.skiptoDTO(media);
    }
}
