package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.service.dto.AppUserDTO;
import com.tutor.auth0r.service.dto.WalletDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Wallet} and its DTO {@link WalletDTO}.
 */
@Mapper(componentModel = "spring")
public interface WalletMapper extends EntityMapper<WalletDTO, Wallet> {
    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    WalletDTO toDto(Wallet s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("RemoveSelftoDTO")
    @Mappings({ @Mapping(target = "appUser", ignore = true) })
    WalletDTO RemoveSelftoDTO(Wallet s);
    // @Named("appUserId2")
    // @BeanMapping(ignoreByDefault = true)
    // @Mapping(target = "id", source = "id")
    // AppUserDTO toDtoAppUserId2(AppUser appUser);

}
