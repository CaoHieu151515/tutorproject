package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.service.dto.FollowCustomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FollowCustomMapper {
    @Named("toFollowCustomDTO")
    @Mapping(source = "appUser.user.firstName", target = "firstName")
    @Mapping(source = "appUser.user.lastName", target = "lastName")
    @Mapping(source = "appUser.user.imageUrl", target = "img")
    FollowCustomDTO toFollowCustomDTO(Tutor tutor);
}
