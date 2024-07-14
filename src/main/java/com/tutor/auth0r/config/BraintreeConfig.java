package com.tutor.auth0r.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BraintreeConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public BraintreeGateway braintreeGateway() {
        ApplicationProperties.BraintreeProperties braintreeProperties = applicationProperties.getBraintree();
        return new BraintreeGateway(
            Environment.SANDBOX,
            braintreeProperties.getMerchantId(),
            braintreeProperties.getPublicKey(),
            braintreeProperties.getPrivateKey()
        );
    }
}
