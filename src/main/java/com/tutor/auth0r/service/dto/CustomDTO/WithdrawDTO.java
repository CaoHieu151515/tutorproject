package com.tutor.auth0r.service.dto.CustomDTO;

public class WithdrawDTO {

    private Long appUserID; //appuser.id
    private Double amount;
    private String bankName; //appUser.bankName
    private String bankNumber; //appUser.bankAccountNumber

    // Getters and setters
    public Long getAppUserID() {
        return appUserID;
    }

    public void setAppUserID(Long appUserID) {
        this.appUserID = appUserID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }
}
