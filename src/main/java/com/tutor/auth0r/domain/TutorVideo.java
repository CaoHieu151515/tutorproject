package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A TutorVideo.
 */
@Entity
@Table(name = "tutor_video")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "academicRank", "identityCard", "tutorImage", "tutorVideo" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Media media;

    @JsonIgnoreProperties(value = { "tutorVideo", "tutorTeaches", "tutorContacts", "tutorImages", "tutor" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "tutorVideo")
    private TutorDetails tutorDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TutorVideo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public TutorVideo media(Media media) {
        this.setMedia(media);
        return this;
    }

    public TutorDetails getTutorDetails() {
        return this.tutorDetails;
    }

    public void setTutorDetails(TutorDetails tutorDetails) {
        if (this.tutorDetails != null) {
            this.tutorDetails.setTutorVideo(null);
        }
        if (tutorDetails != null) {
            tutorDetails.setTutorVideo(this);
        }
        this.tutorDetails = tutorDetails;
    }

    public TutorVideo tutorDetails(TutorDetails tutorDetails) {
        this.setTutorDetails(tutorDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorVideo)) {
            return false;
        }
        return getId() != null && getId().equals(((TutorVideo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorVideo{" +
            "id=" + getId() +
            "}";
    }
}
