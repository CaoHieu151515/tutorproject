package com.tutor.auth0r.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.TutorImage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorImageDTO implements Serializable {

    private Long id;

    private MediaDTO media;

    private TutorDetailsDTO tutorDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaDTO getMedia() {
        return media;
    }

    public void setMedia(MediaDTO media) {
        this.media = media;
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
        if (!(o instanceof TutorImageDTO)) {
            return false;
        }

        TutorImageDTO tutorImageDTO = (TutorImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tutorImageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorImageDTO{" +
            "id=" + getId() +
            ", media=" + getMedia() +
            ", tutorDetails=" + getTutorDetails() +
            "}";
    }
}
