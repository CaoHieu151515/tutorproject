package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.HireTutor;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.service.dto.CustomDTO.WithDrawLISTDTO;
import com.tutor.auth0r.service.dto.HireTutorDTO;
import com.tutor.auth0r.service.dto.WalletDTO;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link WalletTransaction} and its DTO {@link WalletTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface WalletTransactionMapper extends EntityMapper<WalletTransactionDTO, WalletTransaction> {
    WalletTransactionMapper INSTANCE = Mappers.getMapper(WalletTransactionMapper.class);

    @Mapping(target = "wallet", source = "wallet", qualifiedByName = "walletId")
    @Mapping(target = "hireTutor", source = "hireTutor", qualifiedByName = "hireTutorId")
    WalletTransactionDTO toDto(WalletTransaction s);

    @Named("hireTutorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HireTutorDTO toDtoHireTutorId(HireTutor hireTutor);

    @Named("walletId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WalletDTO toDtoWalletId(Wallet wallet);

    @Mapping(source = "wallet.appUser.id", target = "appUserID")
    @Mapping(source = "wallet.appUser.user.firstName", target = "fname")
    @Mapping(source = "wallet.appUser.user.lastName", target = "lname")
    @Mapping(source = "wallet.appUser.user.email", target = "email")
    @Mapping(source = "wallet.appUser.tutor.status", target = "status")
    @Mapping(target = "withdrawTrans.id", source = "id")
    @Mapping(target = "withdrawTrans.amount", source = "amount")
    @Mapping(target = "withdrawTrans.type", source = "type")
    @Mapping(target = "withdrawTrans.status", source = "status")
    @Mapping(target = "withdrawTrans.createAt", source = "createAt")
    WithDrawLISTDTO walletTransactionToWithDrawLISTDTO(WalletTransaction walletTransaction);
}
