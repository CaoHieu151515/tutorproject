package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AcademicRank;
import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.TuTorContactWith;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.domain.TutorTeach;
import com.tutor.auth0r.domain.User;
import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.CustomDTO.ListOfConfirmingDTO;
import com.tutor.auth0r.service.dto.CustomDTO.RankwithImageDTO;
import com.tutor.auth0r.service.dto.CustomDTO.TutorEditProfileDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UpdatecertificateDTO;
import com.tutor.auth0r.service.dto.CustomDTO.UserProfileDTO;
import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import com.tutor.auth0r.service.dto.UserDTO;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import com.tutor.auth0r.service.dto.WalletDTO;
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

    TutorMapper tutorins = Mappers.getMapper(TutorMapper.class);
    WalletMapper walletins = Mappers.getMapper(WalletMapper.class);

    TuTorContactWithMapper conteactINS = Mappers.getMapper(TuTorContactWithMapper.class);

    TutorTeachMapper teachINS = Mappers.getMapper(TutorTeachMapper.class);

    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId_2")
    @Mapping(target = "userVerify", source = "userVerify", qualifiedByName = "userVerifyId_2")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "wallet", source = "wallet", qualifiedByName = "walletmapper")
    AppUserDTO toDto(AppUser s);

    @Named("currenttoDTO")
    @Mappings(
        {
            @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId"),
            @Mapping(target = "userVerify", source = "userVerify", qualifiedByName = "userVerifyId_2"),
            @Mapping(target = "user", source = "user", qualifiedByName = "userId"),
            @Mapping(target = "wallet", source = "wallet", qualifiedByName = "walletmapper"),
        }
    )
    AppUserDTO currenttoDTO(AppUser s);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorDTO tutorId(Tutor tutor);

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
            @Mapping(source = "id", target = "appUserid"),
            @Mapping(source = "user.login", target = "login"),
            @Mapping(source = "user.email", target = "email"),
        }
    )
    ListOfConfirmingDTO toListOfConfirmingDTO(AppUser appUser);

    @Mappings(
        {
            @Mapping(source = "id", target = "appUserid"),
            @Mapping(source = "user.firstName", target = "fname"),
            @Mapping(source = "user.lastName", target = "lname"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "userVerify.school", target = "school"),
            @Mapping(source = "userVerify.studentID", target = "studentID"),
            @Mapping(source = "userVerify.major", target = "major"),
            @Mapping(source = "userVerify.graduationYear", target = "year"),
            @Mapping(source = "userVerify.academicRanks", target = "rankwithImage", qualifiedByName = "academicRankmap"),
        }
    )
    UpdatecertificateDTO toDetailsOfConfirmingDTO(AppUser appUser);

    @Named("academicRankmap")
    static Set<RankwithImageDTO> academicRankmap(Set<AcademicRank> academicRanks) {
        return academicRanks
            .stream()
            .map(rank -> {
                if (rank == null) {
                    return null;
                }
                AcademicRankMapper mapper = Mappers.getMapper(AcademicRankMapper.class);
                return mapper.toRankwithImageDTO(rank);
            })
            .collect(Collectors.toSet());
    }

    @Mappings(
        {
            @Mapping(source = "id", target = "appUserID"),
            @Mapping(source = "user.firstName", target = "firstName"),
            @Mapping(source = "user.lastName", target = "lastName"),
            @Mapping(source = "gender", target = "gender"),
            @Mapping(source = "user.imageUrl", target = "imgUrl"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "bankAccountNumber", target = "bankNumber"),
            @Mapping(source = "bankName", target = "bankName"),
        }
    )
    UserProfileDTO toUserProfileDTO(AppUser appUser);

    @Mappings(
        {
            @Mapping(target = "id", source = "appUserID"),
            @Mapping(target = "user.firstName", source = "firstName"),
            @Mapping(target = "user.lastName", source = "lastName"),
            @Mapping(target = "gender", source = "gender"),
            @Mapping(target = "user.imageUrl", source = "imgUrl"),
            @Mapping(target = "user.email", source = "email"),
            @Mapping(target = "bankAccountNumber", source = "bankNumber"),
            @Mapping(target = "bankName", source = "bankName"),
        }
    )
    void updateAppUserFromDto(UserProfileDTO userProfileDTO, @MappingTarget AppUser appUser);

    @Mapping(source = "id", target = "appUserID")
    @Mapping(source = "beTutor", target = "beTutor")
    @Mapping(source = "user.lastName", target = "lname")
    @Mapping(source = "user.firstName", target = "fname")
    @Mapping(source = "user.imageUrl", target = "image")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "tutor.tutorDetails.information", target = "introduce")
    @Mapping(source = "tutor.price", target = "price")
    @Mapping(source = "tutor.tutorDetails.tutorTeaches", target = "teachs", qualifiedByName = "tutorTeachidd")
    @Mapping(source = "tutor.tutorDetails.tutorContacts", target = "contacts", qualifiedByName = "tutorContactidd")
    TutorEditProfileDTO toTutorEditProfileDTO(AppUser appUser);

    @Named("tutorTeachidd")
    default Set<TutorTeachDTO> tutorTeachidd(Set<TutorTeach> tutorTeaches) {
        if (tutorTeaches == null) {
            return null;
        }
        return tutorTeaches.stream().map(TutorTeachMapper.INSTANCE::toDto).collect(Collectors.toSet());
    }

    @Named("tutorContactidd")
    default Set<TuTorContactWithDTO> tutorContactidd(Set<TuTorContactWith> tuTorContactWith) {
        if (tuTorContactWith == null) {
            return null;
        }
        return tuTorContactWith.stream().map(TuTorContactWithMapper.INSTANCE::toDto).collect(Collectors.toSet());
    }

    @Mapping(target = "id", source = "appUserID")
    @Mapping(target = "beTutor", source = "beTutor")
    @Mapping(target = "user.lastName", source = "lname")
    @Mapping(target = "user.firstName", source = "fname")
    @Mapping(target = "user.imageUrl", source = "image")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "tutor.tutorDetails.information", source = "introduce")
    @Mapping(target = "tutor.price", source = "price")
    // @Mapping(target = "tutor.tutorDetails.tutorTeaches", source = "teachs", qualifiedByName = "tutorTeachDTO")
    // @Mapping(target = "tutor.tutorDetails.tutorContacts", source = "contacts", qualifiedByName = "tutorContactDTO")
    void updateAppUserFromDto(TutorEditProfileDTO dto, @MappingTarget AppUser appUser);

    @Named("tutorTeachDTO")
    default Set<TutorTeach> tutorTeachDTO(Set<TutorTeachDTO> tutorTeachDTOs) {
        if (tutorTeachDTOs == null) {
            return null;
        }
        return tutorTeachDTOs.stream().map(TutorTeachMapper.INSTANCE::toEntity).collect(Collectors.toSet());
    }

    @Named("tutorContactDTO")
    default Set<TuTorContactWith> tutorContactDTO(Set<TuTorContactWithDTO> tuTorContactWithDTOs) {
        if (tuTorContactWithDTOs == null) {
            return null;
        }
        return tuTorContactWithDTOs.stream().map(TuTorContactWithMapper.INSTANCE::toEntity).collect(Collectors.toSet());
    }
}
