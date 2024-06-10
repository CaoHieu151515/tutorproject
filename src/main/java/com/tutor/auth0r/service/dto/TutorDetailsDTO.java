package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.Contact;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.TutorDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorDetailsDTO implements Serializable {

    private Long id;

    private Contact contact;

    private String information;

    private TutorVideoDTO tutorVideo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public TutorVideoDTO getTutorVideo() {
        return tutorVideo;
    }

    public void setTutorVideo(TutorVideoDTO tutorVideo) {
        this.tutorVideo = tutorVideo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorDetailsDTO)) {
            return false;
        }

        TutorDetailsDTO tutorDetailsDTO = (TutorDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tutorDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorDetailsDTO{" +
            "id=" + getId() +
            ", contact='" + getContact() + "'" +
            ", information='" + getInformation() + "'" +
            ", tutorVideo=" + getTutorVideo() +
            "}";
    }
}
