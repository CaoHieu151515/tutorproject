package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutor.auth0r.domain.enumeration.Devide;
import com.tutor.auth0r.domain.enumeration.TuStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tutor.
 */
@Entity
@Table(name = "tutor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recommend")
    private Boolean recommend;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "tu_device")
    private Devide tuDevice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TuStatus status;

    @Column(name = "follower_count")
    private Long followerCount;

    @Column(name = "average_rating", precision = 21, scale = 2)
    private BigDecimal averageRating;

    @JsonIgnoreProperties(value = { "tutorVideo", "tutorTeaches", "tutorImages", "tutor" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private TutorDetails tutorDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    @JsonIgnoreProperties(value = { "appUser", "tutor" }, allowSetters = true)
    private Set<HireTutor> hireTutors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tutor")
    @JsonIgnoreProperties(value = { "tutor" }, allowSetters = true)
    private Set<HiringHours> hiringHours = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tutor")
    @JsonIgnoreProperties(value = { "tutor", "appUser" }, allowSetters = true)
    private Set<Rating> ratings = new HashSet<>();

    @JsonIgnoreProperties(value = { "tutor", "userVerify", "user", "rating", "hireTutors", "wallet" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "tutor")
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tutor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRecommend() {
        return this.recommend;
    }

    public Tutor recommend(Boolean recommend) {
        this.setRecommend(recommend);
        return this;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Double getPrice() {
        return this.price;
    }

    public Tutor price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Devide getTuDevice() {
        return this.tuDevice;
    }

    public Tutor tuDevice(Devide tuDevice) {
        this.setTuDevice(tuDevice);
        return this;
    }

    public void setTuDevice(Devide tuDevice) {
        this.tuDevice = tuDevice;
    }

    public TuStatus getStatus() {
        return this.status;
    }

    public Tutor status(TuStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TuStatus status) {
        this.status = status;
    }

    public Long getFollowerCount() {
        return this.followerCount;
    }

    public Tutor followerCount(Long followerCount) {
        this.setFollowerCount(followerCount);
        return this;
    }

    public void setFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }

    public BigDecimal getAverageRating() {
        return this.averageRating;
    }

    public Tutor averageRating(BigDecimal averageRating) {
        this.setAverageRating(averageRating);
        return this;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public TutorDetails getTutorDetails() {
        return this.tutorDetails;
    }

    public void setTutorDetails(TutorDetails tutorDetails) {
        this.tutorDetails = tutorDetails;
    }

    public Tutor tutorDetails(TutorDetails tutorDetails) {
        this.setTutorDetails(tutorDetails);
        return this;
    }

    public Set<HireTutor> getHireTutors() {
        return this.hireTutors;
    }

    public void setHireTutors(Set<HireTutor> hireTutors) {
        if (this.hireTutors != null) {
            this.hireTutors.forEach(i -> i.setTutor(null));
        }
        if (hireTutors != null) {
            hireTutors.forEach(i -> i.setTutor(this));
        }
        this.hireTutors = hireTutors;
    }

    public Tutor hireTutors(Set<HireTutor> hireTutors) {
        this.setHireTutors(hireTutors);
        return this;
    }

    public Tutor addHireTutor(HireTutor hireTutor) {
        this.hireTutors.add(hireTutor);
        hireTutor.setTutor(this);
        return this;
    }

    public Tutor removeHireTutor(HireTutor hireTutor) {
        this.hireTutors.remove(hireTutor);
        hireTutor.setTutor(null);
        return this;
    }

    public Set<HiringHours> getHiringHours() {
        return this.hiringHours;
    }

    public void setHiringHours(Set<HiringHours> hiringHours) {
        if (this.hiringHours != null) {
            this.hiringHours.forEach(i -> i.setTutor(null));
        }
        if (hiringHours != null) {
            hiringHours.forEach(i -> i.setTutor(this));
        }
        this.hiringHours = hiringHours;
    }

    public Tutor hiringHours(Set<HiringHours> hiringHours) {
        this.setHiringHours(hiringHours);
        return this;
    }

    public Tutor addHiringHours(HiringHours hiringHours) {
        this.hiringHours.add(hiringHours);
        hiringHours.setTutor(this);
        return this;
    }

    public Tutor removeHiringHours(HiringHours hiringHours) {
        this.hiringHours.remove(hiringHours);
        hiringHours.setTutor(null);
        return this;
    }

    public Set<Rating> getRatings() {
        return this.ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        if (this.ratings != null) {
            this.ratings.forEach(i -> i.setTutor(null));
        }
        if (ratings != null) {
            ratings.forEach(i -> i.setTutor(this));
        }
        this.ratings = ratings;
    }

    public void updateAverageRating() {
        if (this.ratings.isEmpty()) {
            this.averageRating = BigDecimal.ZERO;
        } else {
            double sum = this.ratings.stream().mapToDouble(rating -> rating.getRating() != null ? rating.getRating() : 0.0).sum();
            double avg = sum / this.ratings.size();
            this.averageRating = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public Tutor ratings(Set<Rating> ratings) {
        this.setRatings(ratings);
        return this;
    }

    public Tutor addRating(Rating rating) {
        this.ratings.add(rating);
        rating.setTutor(this);

        return this;
    }

    public Tutor removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setTutor(null);
        return this;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        if (this.appUser != null) {
            this.appUser.setTutor(null);
        }
        if (appUser != null) {
            appUser.setTutor(this);
        }
        this.appUser = appUser;
    }

    public Tutor appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tutor)) {
            return false;
        }
        return getId() != null && getId().equals(((Tutor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tutor{" +
            "id=" + getId() +
            ", recommend='" + getRecommend() + "'" +
            ", price=" + getPrice() +
            ", tuDevice='" + getTuDevice() + "'" +
            ", status='" + getStatus() + "'" +
            ", followerCount=" + getFollowerCount() +
            ", averageRating=" + getAverageRating() +
            "}";
    }
}
