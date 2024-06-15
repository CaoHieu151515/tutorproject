package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.dto.RatingDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.dto.UserDTO;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    RatingMapper ratingins = Mappers.getMapper(RatingMapper.class);

    IdentityCardMapper indenIns = Mappers.getMapper(IdentityCardMapper.class);

    UserVerifyMapper verifyMapperins = Mappers.getMapper(UserVerifyMapper.class);

    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId")
    @Mapping(target = "userVerify", source = "userVerify", qualifiedByName = "userVerifyId_2")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "rating", source = "rating", qualifiedByName = "ratingUnestTuTor")
    AppUserDTO toDto(AppUser s);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorDTO toDtoTutorId(Tutor tutor);

    @Named("userVerifyId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    UserVerifyDTO toDtoUserVerifyId(UserVerify userVerify);

    @Named("userId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("ratingId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    RatingDTO toDtoRatingId(Rating rating);

    @Named("ratingUnestTuTor")
    static RatingDTO ratingUnestTuTor(Rating rating) {
        if (rating == null) {
            return null;
        }
        rating.setTutor(null);
        return RatingMapper.INSTANCE.toDto(rating);
    }

    @Named("userVerifyId_2")
    static UserVerifyDTO ratingUnestTuTor(UserVerify userVerify) {
        if (userVerify == null) {
            return null;
        }

        return UserVerifyMapper.INSTANCE.toDto(userVerify);
    }
}
