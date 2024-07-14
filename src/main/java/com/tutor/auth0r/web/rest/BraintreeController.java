package com.tutor.auth0r.web.rest;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.tutor.auth0r.service.BraintreeService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/braintree")
public class BraintreeController {

    private final BraintreeService braintreeService;

    @Autowired
    public BraintreeController(BraintreeService braintreeService) {
        this.braintreeService = braintreeService;
    }

    @GetMapping("/token")
    public ResponseEntity<String> generateClientToken() {
        String clientToken = braintreeService.generateClientToken();
        return ResponseEntity.ok(clientToken);
    }

    @PostMapping("/pay")
    public ResponseEntity<String> createTransaction(@RequestParam String nonce, @RequestParam Double amount) {
        BigDecimal decimalAmount = BigDecimal.valueOf(amount);
        Result<Transaction> result = braintreeService.createTransaction(nonce, decimalAmount);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            return ResponseEntity.ok(transaction.getId()); // Trả về ID của giao dịch
        } else {
            return ResponseEntity.status(500).body("Transaction failed");
        }
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Transaction> findTransaction(@PathVariable String transactionId) {
        Transaction transaction = braintreeService.findTransaction(transactionId);
        return ResponseEntity.ok(transaction);
    }
}
