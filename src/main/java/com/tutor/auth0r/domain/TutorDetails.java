package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TutorDetails.
 */
@Entity
@Table(name = "tutor_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "information")
    private String information;

    @JsonIgnoreProperties(value = { "media", "tutorDetails" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private TutorVideo tutorVideo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tutorDetails")
    @JsonIgnoreProperties(value = { "tutorDetails" }, allowSetters = true)
    private Set<TutorTeach> tutorTeaches = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tutorDetails")
    @JsonIgnoreProperties(value = { "tutorDetails" }, allowSetters = true)
    private Set<TuTorContactWith> tutorContacts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutorDetails")
    @JsonIgnoreProperties(value = { "media", "tutorDetails" }, allowSetters = true)
    private Set<TutorImage> tutorImages = new HashSet<>();

    @JsonIgnoreProperties(value = { "tutorDetails", "hireTutors", "hiringHours", "reports", "ratings", "appUser" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "tutorDetails")
    private Tutor tutor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TutorDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return this.information;
    }

    public TutorDetails information(String information) {
        this.setInformation(information);
        return this;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public TutorVideo getTutorVideo() {
        return this.tutorVideo;
    }

    public void setTutorVideo(TutorVideo tutorVideo) {
        this.tutorVideo = tutorVideo;
    }

    public TutorDetails tutorVideo(TutorVideo tutorVideo) {
        this.setTutorVideo(tutorVideo);
        return this;
    }

    public Set<TutorTeach> getTutorTeaches() {
        return this.tutorTeaches;
    }

    public void setTutorTeaches(Set<TutorTeach> tutorTeaches) {
        if (this.tutorTeaches != null) {
            this.tutorTeaches.forEach(i -> i.setTutorDetails(null));
        }
        if (tutorTeaches != null) {
            tutorTeaches.forEach(i -> i.setTutorDetails(this));
        }
        this.tutorTeaches = tutorTeaches;
    }

    public TutorDetails tutorTeaches(Set<TutorTeach> tutorTeaches) {
        this.setTutorTeaches(tutorTeaches);
        return this;
    }

    public TutorDetails addTutorTeach(TutorTeach tutorTeach) {
        this.tutorTeaches.add(tutorTeach);
        tutorTeach.setTutorDetails(this);
        return this;
    }

    public TutorDetails removeTutorTeach(TutorTeach tutorTeach) {
        this.tutorTeaches.remove(tutorTeach);
        tutorTeach.setTutorDetails(null);
        return this;
    }

    public Set<TuTorContactWith> getTutorContacts() {
        return this.tutorContacts;
    }

    public void setTutorContacts(Set<TuTorContactWith> tuTorContactWiths) {
        if (this.tutorContacts != null) {
            this.tutorContacts.forEach(i -> i.setTutorDetails(null));
        }
        if (tuTorContactWiths != null) {
            tuTorContactWiths.forEach(i -> i.setTutorDetails(this));
        }
        this.tutorContacts = tuTorContactWiths;
    }

    public TutorDetails tutorContacts(Set<TuTorContactWith> tuTorContactWiths) {
        this.setTutorContacts(tuTorContactWiths);
        return this;
    }

    public TutorDetails addTutorContact(TuTorContactWith tuTorContactWith) {
        this.tutorContacts.add(tuTorContactWith);
        tuTorContactWith.setTutorDetails(this);
        return this;
    }

    public TutorDetails removeTutorContact(TuTorContactWith tuTorContactWith) {
        this.tutorContacts.remove(tuTorContactWith);
        tuTorContactWith.setTutorDetails(null);
        return this;
    }

    public Set<TutorImage> getTutorImages() {
        return this.tutorImages;
    }

    public void setTutorImages(Set<TutorImage> tutorImages) {
        if (this.tutorImages != null) {
            this.tutorImages.forEach(i -> i.setTutorDetails(null));
        }
        if (tutorImages != null) {
            tutorImages.forEach(i -> i.setTutorDetails(this));
        }
        this.tutorImages = tutorImages;
    }

    public TutorDetails tutorImages(Set<TutorImage> tutorImages) {
        this.setTutorImages(tutorImages);
        return this;
    }

    public TutorDetails addTutorImage(TutorImage tutorImage) {
        this.tutorImages.add(tutorImage);
        tutorImage.setTutorDetails(this);
        return this;
    }

    public TutorDetails removeTutorImage(TutorImage tutorImage) {
        this.tutorImages.remove(tutorImage);
        tutorImage.setTutorDetails(null);
        return this;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public void setTutor(Tutor tutor) {
        if (this.tutor != null) {
            this.tutor.setTutorDetails(null);
        }
        if (tutor != null) {
            tutor.setTutorDetails(this);
        }
        this.tutor = tutor;
    }

    public TutorDetails tutor(Tutor tutor) {
        this.setTutor(tutor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((TutorDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorDetails{" +
            "id=" + getId() +
            ", information='" + getInformation() + "'" +
            "}";
    }
}
