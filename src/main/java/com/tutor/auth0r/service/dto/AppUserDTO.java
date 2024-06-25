package com.tutor.auth0r.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tutor.auth0r.domain.enumeration.GenderType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.AppUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppUserDTO implements Serializable {

    private Long id;

    private Boolean beTutor;

    private GenderType gender;

    private String bankAccountNumber;

    private String bankName;

    private String walletAddress;

    private TutorDTO tutor;

    private UserVerifyDTO userVerify;

    private WalletDTO wallet;

    private UserDTO user;

    private RatingDTO rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBeTutor() {
        return beTutor;
    }

    public void setBeTutor(Boolean beTutor) {
        this.beTutor = beTutor;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public TutorDTO getTutor() {
        return tutor;
    }

    public void setTutor(TutorDTO tutor) {
        this.tutor = tutor;
    }

    public UserVerifyDTO getUserVerify() {
        return userVerify;
    }

    public void setUserVerify(UserVerifyDTO userVerify) {
        this.userVerify = userVerify;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public WalletDTO getWallet() {
        return wallet;
    }

    public void setWallet(WalletDTO wallet) {
        this.wallet = wallet;
    }

    public RatingDTO getRating() {
        return rating;
    }

    public void setRating(RatingDTO rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserDTO)) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", beTutor='" + getBeTutor() + "'" +
            ", gender='" + getGender() + "'" +
            ", bankAccountNumber='" + getBankAccountNumber() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", walletAddress='" + getWalletAddress() + "'" +
            ", tutor=" + getTutor() +
            ", userVerify=" + getUserVerify() +
            ", user=" + getUser() +
            ", rating=" + getRating() +
            "}";
    }
}
