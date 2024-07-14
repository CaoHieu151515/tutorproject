package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.auth0r.domain.enumeration.WalletTransactionStatus;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A WalletTransaction.
 */
@Entity
@Table(name = "wallet_transaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WalletTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WalletTransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WalletTransactionStatus status;

    @Column(name = "create_at")
    private LocalDate createAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "walletTransaction")
    @JsonIgnoreProperties(value = { "walletTransaction" }, allowSetters = true)
    private Set<ThirdPartyTransaction> thirdPartyTransactions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "walletTransactions", "appUser", "tutor" }, allowSetters = true)
    private HireTutor hireTutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "appUser", "transactions" }, allowSetters = true)
    private Wallet wallet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WalletTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public WalletTransaction amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public WalletTransactionType getType() {
        return this.type;
    }

    public WalletTransaction type(WalletTransactionType type) {
        this.setType(type);
        return this;
    }

    public void setType(WalletTransactionType type) {
        this.type = type;
    }

    public WalletTransactionStatus getStatus() {
        return this.status;
    }

    public WalletTransaction status(WalletTransactionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(WalletTransactionStatus status) {
        this.status = status;
    }

    public LocalDate getCreateAt() {
        return this.createAt;
    }

    public WalletTransaction createAt(LocalDate createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public Set<ThirdPartyTransaction> getThirdPartyTransactions() {
        return this.thirdPartyTransactions;
    }

    public void setThirdPartyTransactions(Set<ThirdPartyTransaction> thirdPartyTransactions) {
        if (this.thirdPartyTransactions != null) {
            this.thirdPartyTransactions.forEach(i -> i.setWalletTransaction(null));
        }
        if (thirdPartyTransactions != null) {
            thirdPartyTransactions.forEach(i -> i.setWalletTransaction(this));
        }
        this.thirdPartyTransactions = thirdPartyTransactions;
    }

    public WalletTransaction thirdPartyTransactions(Set<ThirdPartyTransaction> thirdPartyTransactions) {
        this.setThirdPartyTransactions(thirdPartyTransactions);
        return this;
    }

    public WalletTransaction addThirdPartyTransactions(ThirdPartyTransaction thirdPartyTransaction) {
        this.thirdPartyTransactions.add(thirdPartyTransaction);
        thirdPartyTransaction.setWalletTransaction(this);
        return this;
    }

    public WalletTransaction removeThirdPartyTransactions(ThirdPartyTransaction thirdPartyTransaction) {
        this.thirdPartyTransactions.remove(thirdPartyTransaction);
        thirdPartyTransaction.setWalletTransaction(null);
        return this;
    }

    public HireTutor getHireTutor() {
        return this.hireTutor;
    }

    public void setHireTutor(HireTutor hireTutor) {
        this.hireTutor = hireTutor;
    }

    public WalletTransaction hireTutor(HireTutor hireTutor) {
        this.setHireTutor(hireTutor);
        return this;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public WalletTransaction wallet(Wallet wallet) {
        this.setWallet(wallet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalletTransaction)) {
            return false;
        }
        return getId() != null && getId().equals(((WalletTransaction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalletTransaction{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            "}";
    }
}
