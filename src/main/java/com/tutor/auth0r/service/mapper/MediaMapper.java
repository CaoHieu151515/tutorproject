package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.dto.MediaDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Media} and its DTO {@link MediaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {
    MediaMapper INSTANCE = Mappers.getMapper(MediaMapper.class);
    TutorVideoMapper tuorVideoIns = Mappers.getMapper(TutorVideoMapper.class);

    @Mapping(target = "identityCard", source = "identityCard", qualifiedByName = "identityCardId")
    MediaDTO toDto(Media s);

    @Named("identityCardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IdentityCardDTO toDtoIdentityCardId(IdentityCard identityCard);

    @Named("skiptoDTO")
    @Mappings({ @Mapping(target = "identityCard", ignore = true) })
    MediaDTO skiptoDTO(Media s);
}
