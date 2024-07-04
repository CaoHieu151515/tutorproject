package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.domain.TutorImage;
import com.tutor.auth0r.service.dto.MediaDTO;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import com.tutor.auth0r.service.dto.TutorImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TutorImage} and its DTO {@link TutorImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorImageMapper extends EntityMapper<TutorImageDTO, TutorImage> {
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId2")
    @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId")
    TutorImageDTO toDto(TutorImage s);

    @Named("mediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MediaDTO toDtoMediaId(Media media);

    @Named("tutorDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);

    @Named("mediaId2")
    static MediaDTO mediamapper(Media media) {
        if (media == null) {
            return null;
        }
        return MediaMapper.INSTANCE.skiptoDTO(media);
    }
}
