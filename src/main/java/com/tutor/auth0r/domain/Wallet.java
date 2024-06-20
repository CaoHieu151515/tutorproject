package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.auth0r.web.rest.errors.WalletAmountIsNotEnoughException;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wallet.
 */
@Entity
@Table(name = "wallet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @JsonIgnoreProperties(value = { "tutor", "userVerify", "user", "rating", "hireTutors", "wallet" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private AppUser appUser;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wallet")
    @JsonIgnoreProperties(value = { "wallet" }, allowSetters = true)
    private Set<WalletTransaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wallet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Wallet amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Wallet appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    public Set<WalletTransaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<WalletTransaction> walletTransactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setWallet(null));
        }
        if (walletTransactions != null) {
            walletTransactions.forEach(i -> i.setWallet(this));
        }
        this.transactions = walletTransactions;
    }

    public Wallet transactions(Set<WalletTransaction> walletTransactions) {
        this.setTransactions(walletTransactions);
        return this;
    }

    public Wallet addTransactions(WalletTransaction walletTransaction) {
        this.transactions.add(walletTransaction);
        walletTransaction.setWallet(this);

        Double currentAmount = getAmount();

        switch (walletTransaction.getType()) {
            case WITHDRAWAL:
                if (currentAmount < walletTransaction.getAmount()) {
                    throw new WalletAmountIsNotEnoughException();
                }

                currentAmount -= walletTransaction.getAmount();
                break;
            case DEPOSIT, REFUND, TUTORGAIN:
                currentAmount += walletTransaction.getAmount();
                break;
            case HIRE:
                currentAmount -= walletTransaction.getAmount();
                break;
            case SERVICE_FEE_EARN:
                currentAmount += walletTransaction.getAmount();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + walletTransaction.getType());
        }

        setAmount(currentAmount);

        return this;
    }

    public Wallet removeTransactions(WalletTransaction walletTransaction) {
        this.transactions.remove(walletTransaction);
        walletTransaction.setWallet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wallet)) {
            return false;
        }
        return getId() != null && getId().equals(((Wallet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wallet{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            "}";
    }
}
