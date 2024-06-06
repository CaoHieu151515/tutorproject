package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppUser getAppUserSample1() {
        return new AppUser().id(1L).bankAccountNumber("bankAccountNumber1").bankName("bankName1").walletAddress("walletAddress1");
    }

    public static AppUser getAppUserSample2() {
        return new AppUser().id(2L).bankAccountNumber("bankAccountNumber2").bankName("bankName2").walletAddress("walletAddress2");
    }

    public static AppUser getAppUserRandomSampleGenerator() {
        return new AppUser()
            .id(longCount.incrementAndGet())
            .bankAccountNumber(UUID.randomUUID().toString())
            .bankName(UUID.randomUUID().toString())
            .walletAddress(UUID.randomUUID().toString());
    }
}
