package com.tutor.auth0r.web.rest.vm;

import com.tutor.auth0r.service.dto.AdminUserDTO;
import jakarta.validation.constraints.Size;

public class ManagedUserDetailsVM extends AdminUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    public ManagedUserDetailsVM() {}

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private String gender;

    private String OTP;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    @Override
    public String toString() {
        return "ManagedUserDetailsVM{" + "password='" + password + '\'' + ", gender='" + gender + '\'' + ", OTP='" + OTP + '\'' + '}';
    }
}
