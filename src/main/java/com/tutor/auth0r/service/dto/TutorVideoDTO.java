package com.tutor.auth0r.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.TutorVideo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TutorVideoDTO implements Serializable {

    private Long id;

    private MediaDTO media;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorVideoDTO)) {
            return false;
        }

        TutorVideoDTO tutorVideoDTO = (TutorVideoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tutorVideoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorVideoDTO{" +
            "id=" + getId() +
            ", media=" + getMedia() +
            "}";
    }
}
