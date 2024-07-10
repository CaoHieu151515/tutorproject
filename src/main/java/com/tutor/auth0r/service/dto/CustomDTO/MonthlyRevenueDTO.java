package com.tutor.auth0r.service.dto.CustomDTO;

import com.tutor.auth0r.domain.WalletTransaction;
import java.util.Set;

public class MonthlyRevenueDTO {

    private Double totalRevenue;
    private Set<WalletTransaction> transactions;

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Set<WalletTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<WalletTransaction> transactions) {
        this.transactions = transactions;
    }
}
