package com.tutor.auth0r.web.rest.errors;

public class NotLoggedException extends BadRequestAlertException {

    public NotLoggedException() {
        super(ErrorConstants.NOT_LOGGED_TYPE, "You are not logged in", "user", "notLogged");
    }
}
