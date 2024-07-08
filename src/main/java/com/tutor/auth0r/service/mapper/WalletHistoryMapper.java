package com.tutor.auth0r.service.mapper;

import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.service.dto.CustomDTO.WalletHistoryDTO;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public interface WalletHistoryMapper {
    WalletHistoryMapper INSTANCE = Mappers.getMapper(WalletHistoryMapper.class);
    WalletTransactionMapper transINS = Mappers.getMapper(WalletTransactionMapper.class);

    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "transactions", target = "hireTrans", qualifiedByName = "filterHireTransactions")
    @Mapping(source = "transactions", target = "depositTrans", qualifiedByName = "filterDepositTransactions")
    WalletHistoryDTO toDto(Wallet wallet);

    // @Named("tutorDetailsId")
    // default Set<WalletTransactionDTO> filterHireTransactions(Set<WalletTransaction> transactions) {
    //     return transactions.stream()
    //             .filter(transaction -> "Hire".equals(transaction.getType()))
    //             .map(this::toWalletTransactionDTO)
    //             .collect(Collectors.toSet());
    // }
    // WalletTransactionDTO filterHireTransactions(WalletTransaction transaction);

    @Named("filterDepositTransactions")
    default Set<WalletTransactionDTO> filterDepositTransactions(Set<WalletTransaction> transactions) {
        return transactions.stream().map(WalletTransactionMapper.INSTANCE::toDto).collect(Collectors.toSet());
    }

    @Named("filterHireTransactions")
    default Set<WalletTransactionDTO> filterHireTransactions(Set<WalletTransaction> transactions) {
        return transactions.stream().map(WalletTransactionMapper.INSTANCE::toDto).collect(Collectors.toSet());
    }
}
