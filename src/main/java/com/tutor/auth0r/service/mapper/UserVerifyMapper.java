package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.domain.Media;
import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link UserVerify} and its DTO {@link UserVerifyDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserVerifyMapper extends EntityMapper<UserVerifyDTO, UserVerify> {
    UserVerifyMapper INSTANCE = Mappers.getMapper(UserVerifyMapper.class);

    RatingMapper ratingins = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "identityCard", source = "identityCard", qualifiedByName = "mapToMultiIdentity")
    UserVerifyDTO toDto(UserVerify s);

    // @Named("identityCardId")
    // @BeanMapping(ignoreByDefault = false)
    // @Mapping(target = "id", source = "id")
    // IdentityCardDTO toDtoIdentityCardId(IdentityCard identityCard);

    @Named("mapToMultiIdentity")
    static IdentityCardDTO mapToMultiIdentity(IdentityCard IdentityCard) {
        if (IdentityCard == null) {
            return null;
        }

        return IdentityCardMapper.INSTANCE.RemoveSelftoDTO(IdentityCard);
    }
}
