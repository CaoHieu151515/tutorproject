package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.ThirdPartyTransaction;
import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.service.dto.ThirdPartyTransactionDTO;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThirdPartyTransaction} and its DTO {@link ThirdPartyTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ThirdPartyTransactionMapper extends EntityMapper<ThirdPartyTransactionDTO, ThirdPartyTransaction> {
    @Mapping(target = "walletTransaction", source = "walletTransaction", qualifiedByName = "walletTransactionId")
    ThirdPartyTransactionDTO toDto(ThirdPartyTransaction s);

    @Named("walletTransactionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WalletTransactionDTO toDtoWalletTransactionId(WalletTransaction walletTransaction);
}
