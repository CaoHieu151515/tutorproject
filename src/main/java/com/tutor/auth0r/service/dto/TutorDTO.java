package com.tutor.auth0r.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tutor.auth0r.domain.enumeration.Devide;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.Tutor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TutorDTO implements Serializable {

    private Long id;

    private Boolean recommend;

    private Double price;

    private Devide tuDevice;

    private TuStatus status;

    private Long followerCount;

    private BigDecimal averageRating;

    private TutorDetailsDTO tutorDetails;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<RatingDTO> Rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Devide getTuDevice() {
        return tuDevice;
    }

    public void setTuDevice(Devide tuDevice) {
        this.tuDevice = tuDevice;
    }

    public TuStatus getStatus() {
        return status;
    }

    public void setStatus(TuStatus status) {
        this.status = status;
    }

    public Long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public TutorDetailsDTO getTutorDetails() {
        return tutorDetails;
    }

    public void setTutorDetails(TutorDetailsDTO tutorDetails) {
        this.tutorDetails = tutorDetails;
    }

    public Set<RatingDTO> getRating() {
        return Rating;
    }

    public void setRating(Set<RatingDTO> Rating) {
        this.Rating = Rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorDTO)) {
            return false;
        }

        TutorDTO tutorDTO = (TutorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tutorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorDTO{" +
            "id=" + getId() +
            ", recommend='" + getRecommend() + "'" +
            ", price=" + getPrice() +
            ", tuDevice='" + getTuDevice() + "'" +
            ", status='" + getStatus() + "'" +
            ", followerCount=" + getFollowerCount() +
            ", averageRating=" + getAverageRating() +
            ", tutorDetails=" + getTutorDetails() +
            "}";
    }
}
