package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A HiringHours.
 */
@Entity
@Table(name = "hiring_hours")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HiringHours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hour")
    private Integer hour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutorDetails", "hireTutors", "hiringHours", "ratings", "appUser" }, allowSetters = true)
    private Tutor tutor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HiringHours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHour() {
        return this.hour;
    }

    public HiringHours hour(Integer hour) {
        this.setHour(hour);
        return this;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public HiringHours tutor(Tutor tutor) {
        this.setTutor(tutor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HiringHours)) {
            return false;
        }
        return getId() != null && getId().equals(((HiringHours) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HiringHours{" +
            "id=" + getId() +
            ", hour=" + getHour() +
            "}";
    }
}