package com.tutor.auth0r.web.rest.errors;

public class NotEnoughMoneyException extends BadRequestAlertException {

    public NotEnoughMoneyException() {
        super(ErrorConstants.WALLET_AMOUNT_IS_NOT_ENOUGH, "Wallet amount is not enough", "wallet", "walletAmountIsNotEnough");
    }
}
