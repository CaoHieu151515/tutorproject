package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HireTutorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static HireTutor getHireTutorSample1() {
        return new HireTutor().id(1L).timeHire(1);
    }

    public static HireTutor getHireTutorSample2() {
        return new HireTutor().id(2L).timeHire(2);
    }

    public static HireTutor getHireTutorRandomSampleGenerator() {
        return new HireTutor().id(longCount.incrementAndGet()).timeHire(intCount.incrementAndGet());
    }
}
