package com.tutor.auth0r.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.UserVerify} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserVerifyDTO implements Serializable {

    private Long id;

    private Long rating;

    private String school;

    private String studentID;

    private String major;

    private Long graduationYear;

    private IdentityCardDTO identityCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Long graduationYear) {
        this.graduationYear = graduationYear;
    }

    public IdentityCardDTO getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(IdentityCardDTO identityCard) {
        this.identityCard = identityCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserVerifyDTO)) {
            return false;
        }

        UserVerifyDTO userVerifyDTO = (UserVerifyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userVerifyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserVerifyDTO{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", school='" + getSchool() + "'" +
            ", studentID='" + getStudentID() + "'" +
            ", major='" + getMajor() + "'" +
            ", graduationYear=" + getGraduationYear() +
            ", identityCard=" + getIdentityCard() +
            "}";
    }
}
