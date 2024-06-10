package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.Teach;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.TutorTeach} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorTeachDTO implements Serializable {

    private Long id;

    private Teach subject;

    private TutorDetailsDTO tutorDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teach getSubject() {
        return subject;
    }

    public void setSubject(Teach subject) {
        this.subject = subject;
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
        if (!(o instanceof TutorTeachDTO)) {
            return false;
        }

        TutorTeachDTO tutorTeachDTO = (TutorTeachDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tutorTeachDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorTeachDTO{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", tutorDetails=" + getTutorDetails() +
            "}";
    }
}
