package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Media.
 */
@Entity
@Table(name = "media")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @JsonIgnoreProperties(value = { "media", "userVerify" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "media")
    private AcademicRank academicRank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "media", "userVerify" }, allowSetters = true)
    private IdentityCard identityCard;

    @JsonIgnoreProperties(value = { "media", "tutorDetails" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "media")
    private TutorImage tutorImage;

    @JsonIgnoreProperties(value = { "media", "tutorDetails" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "media")
    private TutorVideo tutorVideo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Media id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public Media url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AcademicRank getAcademicRank() {
        return this.academicRank;
    }

    public void setAcademicRank(AcademicRank academicRank) {
        if (this.academicRank != null) {
            this.academicRank.setMedia(null);
        }
        if (academicRank != null) {
            academicRank.setMedia(this);
        }
        this.academicRank = academicRank;
    }

    public Media academicRank(AcademicRank academicRank) {
        this.setAcademicRank(academicRank);
        return this;
    }

    public IdentityCard getIdentityCard() {
        return this.identityCard;
    }

    public void setIdentityCard(IdentityCard identityCard) {
        this.identityCard = identityCard;
    }

    public Media identityCard(IdentityCard identityCard) {
        this.setIdentityCard(identityCard);
        return this;
    }

    public TutorImage getTutorImage() {
        return this.tutorImage;
    }

    public void setTutorImage(TutorImage tutorImage) {
        if (this.tutorImage != null) {
            this.tutorImage.setMedia(null);
        }
        if (tutorImage != null) {
            tutorImage.setMedia(this);
        }
        this.tutorImage = tutorImage;
    }

    public Media tutorImage(TutorImage tutorImage) {
        this.setTutorImage(tutorImage);
        return this;
    }

    public TutorVideo getTutorVideo() {
        return this.tutorVideo;
    }

    public void setTutorVideo(TutorVideo tutorVideo) {
        if (this.tutorVideo != null) {
            this.tutorVideo.setMedia(null);
        }
        if (tutorVideo != null) {
            tutorVideo.setMedia(this);
        }
        this.tutorVideo = tutorVideo;
    }

    public Media tutorVideo(TutorVideo tutorVideo) {
        this.setTutorVideo(tutorVideo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Media)) {
            return false;
        }
        return getId() != null && getId().equals(((Media) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Media{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
