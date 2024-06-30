package com.tutor.auth0r.service.dto;

import java.util.Set;

public class TuTorCusTomDTO extends TutorDTO {

    private int totalHoursHired;
    private double percentSuccess;
    private String firstName;
    private String lastName;
    private String img;
    private Set<RatingCustomDTO> Cusrating;

    public int getTotalHoursHired() {
        return totalHoursHired;
    }

    public void setTotalHoursHired(int totalHoursHired) {
        this.totalHoursHired = totalHoursHired;
    }

    public double getPercentSuccess() {
        return percentSuccess;
    }

    public void setPercentSuccess(double percentSuccess) {
        this.percentSuccess = percentSuccess;
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

    public Set<RatingCustomDTO> getCusrating() {
        return Cusrating;
    }

    public void setCusrating(Set<RatingCustomDTO> Cusrating) {
        this.Cusrating = Cusrating;
    }
}
