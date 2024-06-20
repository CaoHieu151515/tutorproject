package com.tutor.auth0r.web.rest.errors;

public class AdminWalletNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AdminWalletNotExistException() {
        super("Admin wallet is not exist");
    }
}
