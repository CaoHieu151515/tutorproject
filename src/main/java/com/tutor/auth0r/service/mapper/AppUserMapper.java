package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfConfirmingDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.dto.UserDTO;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import com.tutor.auth0r.service.dto.WalletDTO;
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

    TutorMapper tutorins = Mappers.getMapper(TutorMapper.class);
    WalletMapper walletins = Mappers.getMapper(WalletMapper.class);

    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId_2")
    @Mapping(target = "userVerify", source = "userVerify", qualifiedByName = "userVerifyId_2")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "wallet", source = "wallet", qualifiedByName = "walletmapper")
    AppUserDTO toDto(AppUser s);

    @Named("currenttoDTO")
    @Mappings(
        {
            @Mapping(target = "tutor", ignore = true),
            @Mapping(target = "userVerify", source = "userVerify", qualifiedByName = "userVerifyId_2"),
            @Mapping(target = "user", source = "user", qualifiedByName = "userId"),
            @Mapping(target = "wallet", source = "wallet", qualifiedByName = "walletmapper"),
        }
    )
    AppUserDTO currenttoDTO(AppUser s);

    // @Named("tutorId")
    // @BeanMapping(ignoreByDefault = false)
    // @Mapping(target = "id", source = "id")
    // TutorDTO toDtoTutorId(Tutor tutor);

    @Named("userVerifyId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    UserVerifyDTO toDtoUserVerifyId(UserVerify userVerify);

    @Named("userId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    // @Named("ratingId")
    // @BeanMapping(ignoreByDefault = false)
    // @Mapping(target = "id", source = "id")
    // RatingDTO toDtoRatingId(Rating rating);

    @Named("userVerifyId_2")
    static UserVerifyDTO ratingUnestTuTor(UserVerify userVerify) {
        if (userVerify == null) {
            return null;
        }

        return UserVerifyMapper.INSTANCE.toDto(userVerify);
    }

    @Named("tutorId_2")
    static TutorDTO tutorId_2(Tutor tutor) {
        if (tutor == null) {
            return null;
        }

        return TutorMapper.INSTANCE.toDto(tutor);
    }

    @Named("tutorId_3")
    static TutorDTO tutorId_3(Tutor tutor) {
        if (tutor == null) {
            return null;
        }

        return TutorMapper.INSTANCE.tosimpleDTO(tutor);
    }

    @Named("walletmapper")
    static WalletDTO walletmapper(Wallet wallet) {
        if (wallet == null) {
            return null;
        }
        return WalletMapper.INSTANCE.RemoveSelftoDTO(wallet);
    }

    @Named("toRecommed")
    @Mappings(
        {
            @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId_3"),
            @Mapping(target = "userVerify", ignore = true),
            @Mapping(target = "user", source = "user", qualifiedByName = "userId"),
            @Mapping(target = "rating", ignore = true),
            @Mapping(target = "wallet", ignore = true),
        }
    )
    AppUserDTO toRecommedDTO(AppUser s);

    @Mappings(
        {
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "user.login", target = "login"),
            @Mapping(source = "user.email", target = "email"),
        }
    )
    ListOfConfirmingDTO toListOfConfirmingDTO(AppUser appUser);
}
