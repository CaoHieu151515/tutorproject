package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.WalletTransactionStatus;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.WalletTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WalletTransactionDTO implements Serializable {

    private Long id;

    private Double amount;

    private WalletTransactionType type;

    private WalletTransactionStatus status;

    private LocalDate createAt;

    private HireTutorDTO hireTutor;

    private WalletDTO wallet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public WalletTransactionType getType() {
        return type;
    }

    public void setType(WalletTransactionType type) {
        this.type = type;
    }

    public WalletTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(WalletTransactionStatus status) {
        this.status = status;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public HireTutorDTO getHireTutor() {
        return hireTutor;
    }

    public void setHireTutor(HireTutorDTO hireTutor) {
        this.hireTutor = hireTutor;
    }

    public WalletDTO getWallet() {
        return wallet;
    }

    public void setWallet(WalletDTO wallet) {
        this.wallet = wallet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalletTransactionDTO)) {
            return false;
        }

        WalletTransactionDTO walletTransactionDTO = (WalletTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, walletTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalletTransactionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", hireTutor=" + getHireTutor() +
            ", wallet=" + getWallet() +
            "}";
    }
}
