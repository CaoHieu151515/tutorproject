package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.auth0r.domain.enumeration.Teach;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A TutorTeach.
 */
@Entity
@Table(name = "tutor_teach")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorTeach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private Teach subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutorVideo", "tutorTeaches", "tutorContacts", "tutorImages", "tutor" }, allowSetters = true)
    private TutorDetails tutorDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TutorTeach id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teach getSubject() {
        return this.subject;
    }

    public TutorTeach subject(Teach subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(Teach subject) {
        this.subject = subject;
    }

    public TutorDetails getTutorDetails() {
        return this.tutorDetails;
    }

    public void setTutorDetails(TutorDetails tutorDetails) {
        this.tutorDetails = tutorDetails;
    }

    public TutorTeach tutorDetails(TutorDetails tutorDetails) {
        this.setTutorDetails(tutorDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorTeach)) {
            return false;
        }
        return getId() != null && getId().equals(((TutorTeach) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorTeach{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            "}";
    }
}
