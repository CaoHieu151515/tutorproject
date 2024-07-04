package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.TutorDetails;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfTutorDTO;
import com.tutor.auth0r.service.dto.RatingDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.dto.TutorDetailsDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Tutor} and its DTO {@link TutorDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorMapper extends EntityMapper<TutorDTO, Tutor> {
    TutorMapper INSTANCE = Mappers.getMapper(TutorMapper.class);

    RatingMapper ratingins = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId")
    @Mapping(target = "rating", source = "ratings", qualifiedByName = "ratingUnestTuTor")
    TutorDTO toDto(Tutor s);

    @Named("tutorDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDetailsDTO toDtoTutorDetailsId(TutorDetails tutorDetails);

    @Named("ratingUnestTuTor")
    static RatingDTO ratingUnestTuTor(Rating rating) {
        if (rating == null) {
            return null;
        }
        return RatingMapper.INSTANCE.toDto(rating);
    }

    @Named("FollowShortDto")
    @Mappings({ @Mapping(target = "tutorDetails", ignore = true), @Mapping(target = "rating", ignore = true) })
    TutorDTO FollowShortDto(Tutor s);

    @Named("tosimpleDTO")
    @Mappings(
        {
            @Mapping(target = "rating", ignore = true),
            @Mapping(target = "tutorDetails", source = "tutorDetails", qualifiedByName = "tutorDetailsId"),
        }
    )
    TutorDTO tosimpleDTO(Tutor s);

    @Named("toListDTO")
    @Mappings(
        {
            @Mapping(source = "id", target = "tutorID"),
            @Mapping(source = "appUser.user.firstName", target = "firstName"),
            @Mapping(source = "appUser.user.lastName", target = "lastName"),
            @Mapping(source = "appUser.user.imageUrl", target = "urlImage"),
        }
    )
    ListOfTutorDTO toListDTO(Tutor s);
}
