package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.Rank;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.AcademicRank} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AcademicRankDTO implements Serializable {

    private Long id;

    private Rank type;

    private MediaDTO media;

    private UserVerifyDTO userVerify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rank getType() {
        return type;
    }

    public void setType(Rank type) {
        this.type = type;
    }

    public MediaDTO getMedia() {
        return media;
    }

    public void setMedia(MediaDTO media) {
        this.media = media;
    }

    public UserVerifyDTO getUserVerify() {
        return userVerify;
    }

    public void setUserVerify(UserVerifyDTO userVerify) {
        this.userVerify = userVerify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicRankDTO)) {
            return false;
        }

        AcademicRankDTO academicRankDTO = (AcademicRankDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, academicRankDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicRankDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", media=" + getMedia() +
            ", userVerify=" + getUserVerify() +
            "}";
    }
}
