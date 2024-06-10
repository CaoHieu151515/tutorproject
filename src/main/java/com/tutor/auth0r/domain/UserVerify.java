package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserVerify.
 */
@Entity
@Table(name = "user_verify")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserVerify implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "school")
    private String school;

    @Column(name = "student_id")
    private String studentID;

    @Column(name = "major")
    private String major;

    @Column(name = "graduation_year")
    private Long graduationYear;

    @JsonIgnoreProperties(value = { "media", "userVerify" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private IdentityCard identityCard;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userVerify")
    @JsonIgnoreProperties(value = { "media", "userVerify" }, allowSetters = true)
    private Set<AcademicRank> academicRanks = new HashSet<>();

    @JsonIgnoreProperties(value = { "tutor", "userVerify", "user", "rating", "hireTutors", "wallet" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userVerify")
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserVerify id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRating() {
        return this.rating;
    }

    public UserVerify rating(Long rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getSchool() {
        return this.school;
    }

    public UserVerify school(String school) {
        this.setSchool(school);
        return this;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public UserVerify studentID(String studentID) {
        this.setStudentID(studentID);
        return this;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getMajor() {
        return this.major;
    }

    public UserVerify major(String major) {
        this.setMajor(major);
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getGraduationYear() {
        return this.graduationYear;
    }

    public UserVerify graduationYear(Long graduationYear) {
        this.setGraduationYear(graduationYear);
        return this;
    }

    public void setGraduationYear(Long graduationYear) {
        this.graduationYear = graduationYear;
    }

    public IdentityCard getIdentityCard() {
        return this.identityCard;
    }

    public void setIdentityCard(IdentityCard identityCard) {
        this.identityCard = identityCard;
    }

    public UserVerify identityCard(IdentityCard identityCard) {
        this.setIdentityCard(identityCard);
        return this;
    }

    public Set<AcademicRank> getAcademicRanks() {
        return this.academicRanks;
    }

    public void setAcademicRanks(Set<AcademicRank> academicRanks) {
        if (this.academicRanks != null) {
            this.academicRanks.forEach(i -> i.setUserVerify(null));
        }
        if (academicRanks != null) {
            academicRanks.forEach(i -> i.setUserVerify(this));
        }
        this.academicRanks = academicRanks;
    }

    public UserVerify academicRanks(Set<AcademicRank> academicRanks) {
        this.setAcademicRanks(academicRanks);
        return this;
    }

    public UserVerify addAcademicRank(AcademicRank academicRank) {
        this.academicRanks.add(academicRank);
        academicRank.setUserVerify(this);
        return this;
    }

    public UserVerify removeAcademicRank(AcademicRank academicRank) {
        this.academicRanks.remove(academicRank);
        academicRank.setUserVerify(null);
        return this;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        if (this.appUser != null) {
            this.appUser.setUserVerify(null);
        }
        if (appUser != null) {
            appUser.setUserVerify(this);
        }
        this.appUser = appUser;
    }

    public UserVerify appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserVerify)) {
            return false;
        }
        return getId() != null && getId().equals(((UserVerify) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserVerify{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", school='" + getSchool() + "'" +
            ", studentID='" + getStudentID() + "'" +
            ", major='" + getMajor() + "'" +
            ", graduationYear=" + getGraduationYear() +
            "}";
    }
}
