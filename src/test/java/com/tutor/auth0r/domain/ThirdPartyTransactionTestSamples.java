package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ThirdPartyTransactionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ThirdPartyTransaction getThirdPartyTransactionSample1() {
        return new ThirdPartyTransaction().id(1L).thirdPartyId("thirdPartyId1");
    }

    public static ThirdPartyTransaction getThirdPartyTransactionSample2() {
        return new ThirdPartyTransaction().id(2L).thirdPartyId("thirdPartyId2");
    }

    public static ThirdPartyTransaction getThirdPartyTransactionRandomSampleGenerator() {
        return new ThirdPartyTransaction().id(longCount.incrementAndGet()).thirdPartyId(UUID.randomUUID().toString());
    }
}
