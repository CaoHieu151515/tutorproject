package com.tutor.auth0r.service.dto.CustomDTO;

public class ListOfConfirmingDTO {

    long AppUserid;
    String login;
    String email;

    public long getAppUserid() {
        return AppUserid;
    }

    public void setAppUserid(long AppUserid) {
        this.AppUserid = AppUserid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
