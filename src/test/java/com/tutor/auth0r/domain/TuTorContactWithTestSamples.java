package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TuTorContactWithTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TuTorContactWith getTuTorContactWithSample1() {
        return new TuTorContactWith().id(1L).urlContact("urlContact1");
    }

    public static TuTorContactWith getTuTorContactWithSample2() {
        return new TuTorContactWith().id(2L).urlContact("urlContact2");
    }

    public static TuTorContactWith getTuTorContactWithRandomSampleGenerator() {
        return new TuTorContactWith().id(longCount.incrementAndGet()).urlContact(UUID.randomUUID().toString());
    }
}
