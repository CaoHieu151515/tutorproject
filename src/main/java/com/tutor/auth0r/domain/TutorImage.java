package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A TutorImage.
 */
@Entity
@Table(name = "tutor_image")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "academicRank", "identityCard", "tutorImage", "tutorVideo" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Media media;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutorVideo", "tutorTeaches", "tutorContacts", "tutorImages", "tutor" }, allowSetters = true)
    private TutorDetails tutorDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TutorImage id(Long id) {
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

    public TutorImage media(Media media) {
        this.setMedia(media);
        return this;
    }

    public TutorDetails getTutorDetails() {
        return this.tutorDetails;
    }

    public void setTutorDetails(TutorDetails tutorDetails) {
        this.tutorDetails = tutorDetails;
    }

    public TutorImage tutorDetails(TutorDetails tutorDetails) {
        this.setTutorDetails(tutorDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorImage)) {
            return false;
        }
        return getId() != null && getId().equals(((TutorImage) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorImage{" +
            "id=" + getId() +
            "}";
    }
}
