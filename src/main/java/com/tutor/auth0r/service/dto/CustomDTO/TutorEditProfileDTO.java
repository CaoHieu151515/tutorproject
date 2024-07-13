package com.tutor.auth0r.service.dto.CustomDTO;

import com.tutor.auth0r.service.dto.TuTorContactWithDTO;
import com.tutor.auth0r.service.dto.TutorTeachDTO;
import java.util.Set;

public class TutorEditProfileDTO {

    Long appUserID;
    Boolean beTutor; //appUser.status
    String lname; //appUser.user.lastName
    String fname; //appUser.user.fastName
    String image; //appUser.user.fastName
    String email; //appUser.user.email
    String introduce; //appUser.tutor.tutorDetails.information
    String price; //tutor.price
    Set<TutorTeachDTO> teachs; // appUser.tutor.tutorDetails.tutorTeach
    Set<TuTorContactWithDTO> contacts; // appUser.tutor.tutorDetails.tutorContactWith

    public Long getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(Long appUserID) {
        this.appUserID = appUserID;
    }

    public Boolean getBeTutor() {
        return beTutor;
    }

    public void setBeTutor(Boolean beTutor) {
        this.beTutor = beTutor;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<TutorTeachDTO> getTeachs() {
        return teachs;
    }

    public void setTeachs(Set<TutorTeachDTO> teachs) {
        this.teachs = teachs;
    }

    public Set<TuTorContactWithDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<TuTorContactWithDTO> contacts) {
        this.contacts = contacts;
    }
}
