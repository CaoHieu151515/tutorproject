package com.tutor.auth0r.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A IdentityCard.
 */
@Entity
@Table(name = "identity_card")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IdentityCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "identityCard")
    @JsonIgnoreProperties(value = { "academicRank", "identityCard", "tutorImage", "tutorVideo" }, allowSetters = true)
    private Set<Media> media = new HashSet<>();

    @JsonIgnoreProperties(value = { "identityCard", "academicRanks", "appUser" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "identityCard")
    private UserVerify userVerify;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IdentityCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Media> getMedia() {
        return this.media;
    }

    public void setMedia(Set<Media> media) {
        if (this.media != null) {
            this.media.forEach(i -> i.setIdentityCard(null));
        }
        if (media != null) {
            media.forEach(i -> i.setIdentityCard(this));
        }
        this.media = media;
    }

    public IdentityCard media(Set<Media> media) {
        this.setMedia(media);
        return this;
    }

    public IdentityCard addMedia(Media media) {
        this.media.add(media);
        media.setIdentityCard(this);
        return this;
    }

    public IdentityCard removeMedia(Media media) {
        this.media.remove(media);
        media.setIdentityCard(null);
        return this;
    }

    public UserVerify getUserVerify() {
        return this.userVerify;
    }

    public void setUserVerify(UserVerify userVerify) {
        if (this.userVerify != null) {
            this.userVerify.setIdentityCard(null);
        }
        if (userVerify != null) {
            userVerify.setIdentityCard(this);
        }
        this.userVerify = userVerify;
    }

    public IdentityCard userVerify(UserVerify userVerify) {
        this.setUserVerify(userVerify);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdentityCard)) {
            return false;
        }
        return getId() != null && getId().equals(((IdentityCard) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdentityCard{" +
            "id=" + getId() +
            "}";
    }
}
