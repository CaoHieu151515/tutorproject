package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.HiringHours;
import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.HiringHoursDTO;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.dto.MediaDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IdentityCard} and its DTO {@link IdentityCardDTO}.
 */
@Mapper(componentModel = "spring")
public interface IdentityCardMapper extends EntityMapper<IdentityCardDTO, IdentityCard> {
    // @Mapping(target = "media", source = "media", qualifiedByName = "mediaId")
    // IdentityCardDTO toDto(IdentityCard s);

    // @Named("mediaId")
    // @BeanMapping(ignoreByDefault = false)
    // @Mapping(target = "id", source = "id")
    // MediaDTO toDtoMediaId(Media media);
}
