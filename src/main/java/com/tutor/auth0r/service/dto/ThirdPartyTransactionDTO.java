package com.tutor.auth0r.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.ThirdPartyTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ThirdPartyTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private String thirdPartyId;

    @NotNull
    private LocalDate transactionDate;

    private WalletTransactionDTO walletTransaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public WalletTransactionDTO getWalletTransaction() {
        return walletTransaction;
    }

    public void setWalletTransaction(WalletTransactionDTO walletTransaction) {
        this.walletTransaction = walletTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThirdPartyTransactionDTO)) {
            return false;
        }

        ThirdPartyTransactionDTO thirdPartyTransactionDTO = (ThirdPartyTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thirdPartyTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThirdPartyTransactionDTO{" +
            "id=" + getId() +
            ", thirdPartyId='" + getThirdPartyId() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", walletTransaction=" + getWalletTransaction() +
            "}";
    }
}
