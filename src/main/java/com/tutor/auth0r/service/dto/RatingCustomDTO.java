package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.Rating;

public class RatingCustomDTO extends Rating {

    private String firstName;
    private String lastName;
    private String img;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getimg() {
        return img;
    }

    public void setimg(String img) {
        this.img = img;
    }
}
