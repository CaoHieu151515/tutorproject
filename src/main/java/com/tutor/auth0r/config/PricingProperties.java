package com.tutor.auth0r.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pricing")
public class PricingProperties {

    private double freePercentage;
    private double freePercentageHireGain;
    private int discountPercentage;
    private int maxDiscountAmount;

    // Getters and Setters

    public double getFreePercentage() {
        return freePercentage;
    }

    public void setFreePercentage(double freePercentage) {
        this.freePercentage = freePercentage;
    }

    public double getfreePercentageHireGain() {
        return freePercentageHireGain;
    }

    public void setfreePercentageHireGain(double freePercentageHireGain) {
        this.freePercentageHireGain = freePercentageHireGain;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
}
