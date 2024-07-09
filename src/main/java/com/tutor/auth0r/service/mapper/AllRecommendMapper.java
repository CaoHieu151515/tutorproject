package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.service.dto.CustomDTO.AllRecommendDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllRecommendMapper {
    @Mapping(source = "tutor.id", target = "tutorId")
    @Mapping(source = "user.imageUrl", target = "avatar")
    @Mapping(source = "user.firstName", target = "fname")
    @Mapping(source = "user.lastName", target = "LName")
    @Mapping(source = "tutor.status", target = "status")
    AllRecommendDTO appUserToAllRecommendDTO(AppUser appUser);
}
