package com.tutor.auth0r.service.dto.CustomDTO;

import com.tutor.auth0r.domain.enumeration.TuStatus;
import java.util.Set;

public class UpdatecertificateDTO {

    Long appUserid;
    String lname;
    String fname;
    TuStatus status;
    String email;
    String school;
    String studentID;
    String major;
    Long year;
    private Set<RankwithImageDTO> rankwithImage;

    // Getter và Setter cho id
    public Long getAppUserid() {
        return appUserid;
    }

    public void setAppUserid(Long appUserid) {
        this.appUserid = appUserid;
    }

    // Getter và Setter cho school
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    // Getter và Setter cho studentID
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // Getter và Setter cho major
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Set<RankwithImageDTO> getRankwithImage() {
        return rankwithImage;
    }

    public void setRankwithImage(Set<RankwithImageDTO> rankwithImage) {
        this.rankwithImage = rankwithImage;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TuStatus getStatus() {
        return status;
    }

    public void setStatus(TuStatus status) {
        this.status = status;
    }
}
