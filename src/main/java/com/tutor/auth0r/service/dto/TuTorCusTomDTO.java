package com.tutor.auth0r.service.dto;

import com.tutor.auth0r.domain.enumeration.TuStatus;
import java.util.Set;

public class TuTorCusTomDTO {

    private Long tutorID;
    private int totalHoursHired;
    private double percentSuccess;
    private String firstName;
    private String lastName;
    private String img;
    private Double price;
    private Double averageRate;
    private Set<TutorTeachDTO> teach;
    private TuStatus status;
    private String information;
    private Set<TuTorContactWithDTO> contact;
    private String videoUrl;
    private Set<RatingCustomDTO> Cusrating;

    // Getter and Setter for tutorID
    public Long getTutorID() {
        return tutorID;
    }

    public void setTutorID(Long tutorID) {
        this.tutorID = tutorID;
    }

    // Getter and Setter for totalHoursHired
    public int getTotalHoursHired() {
        return totalHoursHired;
    }

    public void setTotalHoursHired(int totalHoursHired) {
        this.totalHoursHired = totalHoursHired;
    }

    // Getter and Setter for percentSuccess
    public double getPercentSuccess() {
        return percentSuccess;
    }

    public void setPercentSuccess(double percentSuccess) {
        this.percentSuccess = percentSuccess;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for img
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    // Getter and Setter for price
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Getter and Setter for averageRate
    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    // Getter and Setter for teach
    public Set<TutorTeachDTO> getTeach() {
        return teach;
    }

    public void setTeach(Set<TutorTeachDTO> teach) {
        this.teach = teach;
    }

    // Getter and Setter for status
    public TuStatus getStatus() {
        return status;
    }

    public void setStatus(TuStatus status) {
        this.status = status;
    }

    // Getter and Setter for information
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    // Getter and Setter for contact
    public Set<TuTorContactWithDTO> getContact() {
        return contact;
    }

    public void setContact(Set<TuTorContactWithDTO> contact) {
        this.contact = contact;
    }

    // Getter and Setter for videoUrl
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    // Getter and Setter for Cusrating
    public Set<RatingCustomDTO> getCusrating() {
        return Cusrating;
    }

    public void setCusrating(Set<RatingCustomDTO> Cusrating) {
        this.Cusrating = Cusrating;
    }
}
