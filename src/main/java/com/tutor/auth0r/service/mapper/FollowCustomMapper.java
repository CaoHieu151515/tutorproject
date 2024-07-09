package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.FollowCustomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FollowCustomMapper {
    FollowCustomMapper INSTANCE = Mappers.getMapper(FollowCustomMapper.class);
    AppUserMapper UserINSTANCE = Mappers.getMapper(AppUserMapper.class);
    TutorMapper TutorINSTANCE = Mappers.getMapper(TutorMapper.class);

    @Named("toFollowCustomDTO")
    @Mappings(
        {
            @Mapping(source = "id", target = "tutorId"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "appUser.user.firstName", target = "firstName"),
            @Mapping(source = "appUser.user.lastName", target = "lastName"),
            @Mapping(source = "appUser.user.imageUrl", target = "img"),
        }
    )
    FollowCustomDTO toFollowCustomDTO(Tutor tutor);
}
