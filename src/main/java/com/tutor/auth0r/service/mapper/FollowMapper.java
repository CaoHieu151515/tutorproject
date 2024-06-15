package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Follow;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.FollowDTO;
import com.tutor.auth0r.service.dto.TutorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Follow} and its DTO {@link FollowDTO}.
 */
@Mapper(componentModel = "spring")
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {
    @Mapping(target = "followerAppUser", source = "followerAppUser", qualifiedByName = "appUserId")
    @Mapping(target = "followedTutor", source = "followedTutor", qualifiedByName = "tutorId")
    FollowDTO toDto(Follow s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    TutorDTO toDtoTutorId(Tutor tutor);
}
