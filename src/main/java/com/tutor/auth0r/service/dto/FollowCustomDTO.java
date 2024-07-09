package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.TuStatus;

public class FollowCustomDTO {

    private Long tutorId;
    private TuStatus status;
    private String firstName;
    private String lastName;
    private String img;

    // Getter and Setter for tutorId
    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    // Getter and Setter for status
    public TuStatus getStatus() {
        return status;
    }

    public void setStatus(TuStatus status) {
        this.status = status;
    }

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
