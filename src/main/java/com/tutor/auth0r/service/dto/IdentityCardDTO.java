package com.tutor.auth0r.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tutor.auth0r.domain.IdentityCard} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IdentityCardDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdentityCardDTO)) {
            return false;
        }

        IdentityCardDTO identityCardDTO = (IdentityCardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, identityCardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdentityCardDTO{" +
            "id=" + getId() +
            "}";
    }
}
