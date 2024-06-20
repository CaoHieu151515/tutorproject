package com.tutor.auth0r.web.rest.errors;

public class UserNotExistException extends BadRequestAlertException {

    public UserNotExistException() {
        super("User does not exist", "user", "userNotExist");
    }
}
