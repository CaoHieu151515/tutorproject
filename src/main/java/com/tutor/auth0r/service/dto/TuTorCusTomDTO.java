package com.tutor.auth0r.service.dto;

public class TuTorCusTomDTO extends TutorDTO {

    private int totalHoursHired;
    private double percentSuccess;

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
}
