package com.tutor.auth0r.service;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.tutor.auth0r.domain.Wallet;
import com.tutor.auth0r.domain.WalletTransaction;
import com.tutor.auth0r.domain.enumeration.WalletTransactionStatus;
import com.tutor.auth0r.domain.enumeration.WalletTransactionType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BraintreeService {

    private final BraintreeGateway braintreeGateway;
    private final WalletService walletService;

    @Autowired
    public BraintreeService(BraintreeGateway braintreeGateway, WalletService walletService) {
        this.braintreeGateway = braintreeGateway;
        this.walletService = walletService;
    }

    public String generateClientToken() {
        return braintreeGateway.clientToken().generate();
    }

    public Result<Transaction> createTransaction(String nonce, BigDecimal amount) {
        TransactionRequest request = new TransactionRequest()
            .amount(amount)
            .paymentMethodNonce(nonce)
            .options()
            .submitForSettlement(true)
            .done();

        updateBalance(amount);
        return braintreeGateway.transaction().sale(request);
    }

    public Transaction findTransaction(String transactionId) {
        return braintreeGateway.transaction().find(transactionId);
    }

    public void updateBalance(BigDecimal amount) {
        Wallet curentWallet = walletService.getCurrentUserWallet();
        WalletTransaction trans = new WalletTransaction();

        trans.setAmount(amount.doubleValue());
        trans.setType(WalletTransactionType.DEPOSIT);
        trans.setStatus(WalletTransactionStatus.SUCCEED);
        trans.setCreateAt(Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
        curentWallet.addTransactions(trans);
        walletService.save(curentWallet);
    }
}