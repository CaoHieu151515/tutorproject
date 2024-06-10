package com.tutor.auth0r.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.Follow} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FollowDTO implements Serializable {

    private Long id;

    private LocalDate createDate;

    private AppUserDTO followerAppUser;

    private TutorDTO followedTutor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public AppUserDTO getFollowerAppUser() {
        return followerAppUser;
    }

    public void setFollowerAppUser(AppUserDTO followerAppUser) {
        this.followerAppUser = followerAppUser;
    }

    public TutorDTO getFollowedTutor() {
        return followedTutor;
    }

    public void setFollowedTutor(TutorDTO followedTutor) {
        this.followedTutor = followedTutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FollowDTO)) {
            return false;
        }

        FollowDTO followDTO = (FollowDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, followDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FollowDTO{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", followerAppUser=" + getFollowerAppUser() +
            ", followedTutor=" + getFollowedTutor() +
            "}";
    }
}
