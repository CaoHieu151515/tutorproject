package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.HireStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.HireTutor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HireTutorDTO implements Serializable {

    private Long id;

    private Integer timeHire;

    private Double totalPrice;

    private HireStatus status;

    private AppUserDTO appUser;

    private TutorDTO tutor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimeHire() {
        return timeHire;
    }

    public void setTimeHire(Integer timeHire) {
        this.timeHire = timeHire;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HireStatus getStatus() {
        return status;
    }

    public void setStatus(HireStatus status) {
        this.status = status;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
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
        if (!(o instanceof HireTutorDTO)) {
            return false;
        }

        HireTutorDTO hireTutorDTO = (HireTutorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hireTutorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HireTutorDTO{" +
            "id=" + getId() +
            ", timeHire=" + getTimeHire() +
            ", totalPrice=" + getTotalPrice() +
            ", status='" + getStatus() + "'" +
            ", appUser=" + getAppUser() +
            ", tutor=" + getTutor() +
            "}";
    }
}
