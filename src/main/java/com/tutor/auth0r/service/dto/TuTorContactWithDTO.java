package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.Contact;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.TuTorContactWith} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TuTorContactWithDTO implements Serializable {

    private Long id;

    private String urlContact;

    private Contact type;

    private TutorDetailsDTO tutorDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlContact() {
        return urlContact;
    }

    public void setUrlContact(String urlContact) {
        this.urlContact = urlContact;
    }

    public Contact getType() {
        return type;
    }

    public void setType(Contact type) {
        this.type = type;
    }

    public TutorDetailsDTO getTutorDetails() {
        return tutorDetails;
    }

    public void setTutorDetails(TutorDetailsDTO tutorDetails) {
        this.tutorDetails = tutorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TuTorContactWithDTO)) {
            return false;
        }

        TuTorContactWithDTO tuTorContactWithDTO = (TuTorContactWithDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tuTorContactWithDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TuTorContactWithDTO{" +
            "id=" + getId() +
            ", urlContact='" + getUrlContact() + "'" +
            ", type='" + getType() + "'" +
            ", tutorDetails=" + getTutorDetails() +
            "}";
    }
}
