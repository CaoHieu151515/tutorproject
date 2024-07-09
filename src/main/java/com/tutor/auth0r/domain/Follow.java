package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Follow.
 */
@Entity
@Table(name = "follow")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutor", "userVerify", "user", "hireTutors", "reports", "wallet", "ratings" }, allowSetters = true)
    private AppUser followerAppUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutorDetails", "hireTutors", "hiringHours", "reports", "ratings", "appUser" }, allowSetters = true)
    private Tutor followedTutor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Follow id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public Follow createDate(LocalDate createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public AppUser getFollowerAppUser() {
        return this.followerAppUser;
    }

    public void setFollowerAppUser(AppUser appUser) {
        this.followerAppUser = appUser;
    }

    public Follow followerAppUser(AppUser appUser) {
        this.setFollowerAppUser(appUser);
        return this;
    }

    public Tutor getFollowedTutor() {
        return this.followedTutor;
    }

    public void setFollowedTutor(Tutor tutor) {
        this.followedTutor = tutor;
    }

    public Follow followedTutor(Tutor tutor) {
        this.setFollowedTutor(tutor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Follow)) {
            return false;
        }
        return getId() != null && getId().equals(((Follow) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Follow{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
