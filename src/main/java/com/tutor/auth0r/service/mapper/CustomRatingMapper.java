package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Rating;
import com.tutor.auth0r.service.dto.RatingCustomDTO;
import com.tutor.auth0r.service.dto.RatingDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomRatingMapper {
    CustomRatingMapper INSTANCE = Mappers.getMapper(CustomRatingMapper.class);
    RatingMapper ratingins = Mappers.getMapper(RatingMapper.class);

    @Named("toRatingCustomDTO")
    @Mapping(source = "appUser.user.firstName", target = "firstName")
    @Mapping(source = "appUser.user.lastName", target = "lastName")
    @Mapping(source = "appUser.user.imageUrl", target = "img")
    @Mapping(target = "appUser", ignore = true)
    @Mapping(target = "tutor", ignore = true)
    RatingCustomDTO toRatingCustomDTO(Rating Rating);
}
