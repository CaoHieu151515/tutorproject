package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.IdentityCard;
import com.tutor.auth0r.service.dto.IdentityCardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IdentityCard} and its DTO {@link IdentityCardDTO}.
 */
@Mapper(componentModel = "spring")
public interface IdentityCardMapper extends EntityMapper<IdentityCardDTO, IdentityCard> {}
