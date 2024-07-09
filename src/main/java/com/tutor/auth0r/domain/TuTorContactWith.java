package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.auth0r.domain.enumeration.Contact;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A TuTorContactWith.
 */
@Entity
@Table(name = "tu_tor_contact_with")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TuTorContactWith implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url_contact")
    private String urlContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Contact type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutorVideo", "tutorTeaches", "tutorContacts", "tutorImages", "tutor" }, allowSetters = true)
    private TutorDetails tutorDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TuTorContactWith id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlContact() {
        return this.urlContact;
    }

    public TuTorContactWith urlContact(String urlContact) {
        this.setUrlContact(urlContact);
        return this;
    }

    public void setUrlContact(String urlContact) {
        this.urlContact = urlContact;
    }

    public Contact getType() {
        return this.type;
    }

    public TuTorContactWith type(Contact type) {
        this.setType(type);
        return this;
    }

    public void setType(Contact type) {
        this.type = type;
    }

    public TutorDetails getTutorDetails() {
        return this.tutorDetails;
    }

    public void setTutorDetails(TutorDetails tutorDetails) {
        this.tutorDetails = tutorDetails;
    }

    public TuTorContactWith tutorDetails(TutorDetails tutorDetails) {
        this.setTutorDetails(tutorDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TuTorContactWith)) {
            return false;
        }
        return getId() != null && getId().equals(((TuTorContactWith) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TuTorContactWith{" +
            "id=" + getId() +
            ", urlContact='" + getUrlContact() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
