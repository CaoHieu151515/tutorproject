package com.tutor.auth0r.service.dto.CustomDTO;

import com.tutor.auth0r.service.dto.WalletTransactionDTO;
import java.util.Set;

public class WalletHistoryDTO {

    private Double amount;
    private Set<WalletTransactionDTO> hireTrans;
    private Set<WalletTransactionDTO> depositTrans;

    // Getter for amount
    public Double getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    // Getter for hireTrans
    public Set<WalletTransactionDTO> getHireTrans() {
        return hireTrans;
    }

    // Setter for hireTrans
    public void setHireTrans(Set<WalletTransactionDTO> hireTrans) {
        this.hireTrans = hireTrans;
    }

    // Getter for depositTrans
    public Set<WalletTransactionDTO> getDepositTrans() {
        return depositTrans;
    }

    // Setter for depositTrans
    public void setDepositTrans(Set<WalletTransactionDTO> depositTrans) {
        this.depositTrans = depositTrans;
    }
}
