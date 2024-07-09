package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.auth0r.domain.enumeration.Rank;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A AcademicRank.
 */
@Entity
@Table(name = "academic_rank")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcademicRank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Rank type;

    @JsonIgnoreProperties(value = { "academicRank", "identityCard", "tutorImage", "tutorVideo" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private Media media;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "identityCard", "academicRanks", "appUser" }, allowSetters = true)
    private UserVerify userVerify;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AcademicRank id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rank getType() {
        return this.type;
    }

    public AcademicRank type(Rank type) {
        this.setType(type);
        return this;
    }

    public void setType(Rank type) {
        this.type = type;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public AcademicRank media(Media media) {
        this.setMedia(media);
        return this;
    }

    public UserVerify getUserVerify() {
        return this.userVerify;
    }

    public void setUserVerify(UserVerify userVerify) {
        this.userVerify = userVerify;
    }

    public AcademicRank userVerify(UserVerify userVerify) {
        this.setUserVerify(userVerify);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicRank)) {
            return false;
        }
        return getId() != null && getId().equals(((AcademicRank) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicRank{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
