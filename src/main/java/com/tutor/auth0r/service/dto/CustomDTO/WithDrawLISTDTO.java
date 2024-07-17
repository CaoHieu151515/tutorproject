package com.tutor.auth0r.service.dto.CustomDTO;

import com.tutor.auth0r.domain.enumeration.TuStatus;
import com.tutor.auth0r.service.dto.WalletTransactionDTO;

public class WithDrawLISTDTO {

    String appUserID;
    String fname;
    String lname;
    String email;
    TuStatus status;
    WalletTransactionDTO withdrawTrans;

    public String getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(String appUserID) {
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

    public WalletTransactionDTO getWithdrawTrans() {
        return withdrawTrans;
    }

    public void setWithdrawTrans(WalletTransactionDTO withdrawTrans) {
        this.withdrawTrans = withdrawTrans;
    }
}
