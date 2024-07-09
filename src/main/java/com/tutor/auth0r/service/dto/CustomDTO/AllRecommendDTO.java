package com.tutor.auth0r.service.dto.CustomDTO;

import com.tutor.auth0r.domain.enumeration.TuStatus;

public class AllRecommendDTO {

    private Long tutorId;
    private String avatar;
    private String fname;
    private String lName;
    private TuStatus status;

    // Getter for AppUserId
    public Long getTutorId() {
        return tutorId;
    }

    // Setter for AppUserId
    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    // Getter for avatar
    public String getAvatar() {
        return avatar;
    }

    // Setter for avatar
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    // Getter for fname
    public String getFname() {
        return fname;
    }

    // Setter for fname
    public void setFname(String fname) {
        this.fname = fname;
    }

    // Getter for lName
    public String getLName() {
        return lName;
    }

    // Setter for lName
    public void setLName(String lName) {
        this.lName = lName;
    }

    // Getter for status
    public TuStatus getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(TuStatus status) {
        this.status = status;
    }
}
