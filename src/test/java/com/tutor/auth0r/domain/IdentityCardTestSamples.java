package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IdentityCardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IdentityCard getIdentityCardSample1() {
        return new IdentityCard().id(1L);
    }

    public static IdentityCard getIdentityCardSample2() {
        return new IdentityCard().id(2L);
    }

    public static IdentityCard getIdentityCardRandomSampleGenerator() {
        return new IdentityCard().id(longCount.incrementAndGet());
    }
}
