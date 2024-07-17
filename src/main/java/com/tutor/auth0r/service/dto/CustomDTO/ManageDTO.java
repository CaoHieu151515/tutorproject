package com.tutor.auth0r.service.dto.CustomDTO;

import com.tutor.auth0r.domain.Authority;
import java.util.Set;

public class ManageDTO {

    Long appUserID;
    String fname; //appuser.user.firstname
    String lname; //appuser.user.lastname
    Double amount; //appuser.wallet.amount
    String gender;
    Set<Authority> role; //appuser.user.authority

    public Long getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(Long appUserID) {
        this.appUserID = appUserID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<Authority> getRole() {
        return role;
    }

    public void setRole(Set<Authority> role) {
        this.role = role;
    }
}
