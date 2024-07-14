package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ThirdPartyTransaction.
 */
@Entity
@Table(name = "third_party_transaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ThirdPartyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "third_party_id", nullable = false)
    private String thirdPartyId;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "thirdPartyTransactions", "hireTutor", "wallet" }, allowSetters = true)
    private WalletTransaction walletTransaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ThirdPartyTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThirdPartyId() {
        return this.thirdPartyId;
    }

    public ThirdPartyTransaction thirdPartyId(String thirdPartyId) {
        this.setThirdPartyId(thirdPartyId);
        return this;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }

    public ThirdPartyTransaction transactionDate(LocalDate transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public WalletTransaction getWalletTransaction() {
        return this.walletTransaction;
    }

    public void setWalletTransaction(WalletTransaction walletTransaction) {
        this.walletTransaction = walletTransaction;
    }

    public ThirdPartyTransaction walletTransaction(WalletTransaction walletTransaction) {
        this.setWalletTransaction(walletTransaction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThirdPartyTransaction)) {
            return false;
        }
        return getId() != null && getId().equals(((ThirdPartyTransaction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThirdPartyTransaction{" +
            "id=" + getId() +
            ", thirdPartyId='" + getThirdPartyId() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            "}";
    }
}
