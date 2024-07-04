package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.HiringHours;
import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.HiringHoursDTO;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.dto.MediaDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link IdentityCard} and its DTO {@link IdentityCardDTO}.
 */
@Mapper(componentModel = "spring", uses = { MediaMapper.class })
public interface IdentityCardMapper extends EntityMapper<IdentityCardDTO, IdentityCard> {
    IdentityCardMapper INSTANCE = Mappers.getMapper(IdentityCardMapper.class);

    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId")
    IdentityCardDTO toDto(IdentityCard s);

    @Named("mediaId")
    static Set<MediaDTO> mapToMultimedia(Set<Media> mediaa) {
        return mediaa
            .stream()
            .map(media -> {
                if (media == null) {
                    return null;
                }
                return MediaMapper.INSTANCE.skiptoDTO(media);
            })
            .collect(Collectors.toSet());
    }

    @Named("RemoveSelftoDTO")
    @Mappings({ @Mapping(target = "media", ignore = true) })
    IdentityCardDTO RemoveSelftoDTO(IdentityCard s);

    @Named("RemoveSelfId")
    static Set<MediaDTO> RemoveSelfId(Set<Media> mediaa) {
        return mediaa
            .stream()
            .map(media -> {
                if (media == null) {
                    return null;
                }
                return MediaMapper.INSTANCE.toDto(media);
            })
            .collect(Collectors.toSet());
    }
}
