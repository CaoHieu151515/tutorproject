package com.tutor.auth0r.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.HiringHours} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HiringHoursDTO implements Serializable {

    private Long id;

    private Integer hour;

    private TutorDTO tutor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public TutorDTO getTutor() {
        return tutor;
    }

    public void setTutor(TutorDTO tutor) {
        this.tutor = tutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HiringHoursDTO)) {
            return false;
        }

        HiringHoursDTO hiringHoursDTO = (HiringHoursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hiringHoursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HiringHoursDTO{" +
            "id=" + getId() +
            ", hour=" + getHour() +
            ", tutor=" + getTutor() +
            "}";
    }
}
