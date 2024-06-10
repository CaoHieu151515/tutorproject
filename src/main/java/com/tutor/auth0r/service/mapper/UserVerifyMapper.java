package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.domain.UserVerify;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import com.tutor.auth0r.service.dto.UserVerifyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserVerify} and its DTO {@link UserVerifyDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserVerifyMapper extends EntityMapper<UserVerifyDTO, UserVerify> {
    @Mapping(target = "identityCard", source = "identityCard", qualifiedByName = "identityCardId")
    UserVerifyDTO toDto(UserVerify s);

    @Named("identityCardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IdentityCardDTO toDtoIdentityCardId(IdentityCard identityCard);
}
