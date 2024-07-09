package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report_details")
    private String reportDetails;

    @Column(name = "time")
    private LocalDate time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutor", "userVerify", "user", "hireTutors", "reports", "wallet", "ratings" }, allowSetters = true)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tutorDetails", "hireTutors", "hiringHours", "reports", "ratings", "appUser" }, allowSetters = true)
    private Tutor tutor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Report id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportDetails() {
        return this.reportDetails;
    }

    public Report reportDetails(String reportDetails) {
        this.setReportDetails(reportDetails);
        return this;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
    }

    public LocalDate getTime() {
        return this.time;
    }

    public Report time(LocalDate time) {
        this.setTime(time);
        return this;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Report appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Report tutor(Tutor tutor) {
        this.setTutor(tutor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        return getId() != null && getId().equals(((Report) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", reportDetails='" + getReportDetails() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
