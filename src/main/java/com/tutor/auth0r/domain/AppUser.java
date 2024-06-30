package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.auth0r.domain.enumeration.GenderType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "be_tutor")
    private Boolean beTutor;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "wallet_address")
    private String walletAddress;

    @JsonIgnoreProperties(value = { "tutorDetails", "hireTutors", "hiringHours", "ratings", "appUser" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Tutor tutor;

    @JsonIgnoreProperties(value = { "identityCard", "academicRanks", "appUser" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private UserVerify userVerify;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "tutor", "appUser" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Rating rating;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    @JsonIgnoreProperties(value = { "appUser", "tutor" }, allowSetters = true)
    private Set<HireTutor> hireTutors = new HashSet<>();

    @JsonIgnoreProperties(value = { "appUser", "transactions" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appUser")
    private Wallet wallet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBeTutor() {
        return this.beTutor;
    }

    public AppUser beTutor(Boolean beTutor) {
        this.setBeTutor(beTutor);
        return this;
    }

    public void setBeTutor(Boolean beTutor) {
        this.beTutor = beTutor;
    }

    public GenderType getGender() {
        return this.gender;
    }

    public AppUser gender(GenderType gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getBankAccountNumber() {
        return this.bankAccountNumber;
    }

    public AppUser bankAccountNumber(String bankAccountNumber) {
        this.setBankAccountNumber(bankAccountNumber);
        return this;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public AppUser bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getWalletAddress() {
        return this.walletAddress;
    }

    public AppUser walletAddress(String walletAddress) {
        this.setWalletAddress(walletAddress);
        return this;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public AppUser tutor(Tutor tutor) {
        this.setTutor(tutor);
        return this;
    }

    public UserVerify getUserVerify() {
        return this.userVerify;
    }

    public void setUserVerify(UserVerify userVerify) {
        this.userVerify = userVerify;
    }

    public AppUser userVerify(UserVerify userVerify) {
        this.setUserVerify(userVerify);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppUser user(User user) {
        this.setUser(user);
        return this;
    }

    public Rating getRating() {
        return this.rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public AppUser rating(Rating rating) {
        this.setRating(rating);
        return this;
    }

    public Set<HireTutor> getHireTutors() {
        return this.hireTutors;
    }

    public void setHireTutors(Set<HireTutor> hireTutors) {
        if (this.hireTutors != null) {
            this.hireTutors.forEach(i -> i.setAppUser(null));
        }
        if (hireTutors != null) {
            hireTutors.forEach(i -> i.setAppUser(this));
        }
        this.hireTutors = hireTutors;
    }

    public AppUser hireTutors(Set<HireTutor> hireTutors) {
        this.setHireTutors(hireTutors);
        return this;
    }

    public AppUser addHireTutor(HireTutor hireTutor) {
        this.hireTutors.add(hireTutor);
        hireTutor.setAppUser(this);
        return this;
    }

    public AppUser removeHireTutor(HireTutor hireTutor) {
        this.hireTutors.remove(hireTutor);
        hireTutor.setAppUser(null);
        return this;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void setWallet(Wallet wallet) {
        if (this.wallet != null) {
            this.wallet.setAppUser(null);
        }
        if (wallet != null) {
            wallet.setAppUser(this);
        }
        this.wallet = wallet;
    }

    public AppUser wallet(Wallet wallet) {
        this.setWallet(wallet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return getId() != null && getId().equals(((AppUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", beTutor='" + getBeTutor() + "'" +
            ", gender='" + getGender() + "'" +
            ", bankAccountNumber='" + getBankAccountNumber() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", walletAddress='" + getWalletAddress() + "'" +
            "}";
    }
}
