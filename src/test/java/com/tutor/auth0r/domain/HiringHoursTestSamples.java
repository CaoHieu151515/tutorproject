package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HiringHoursTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static HiringHours getHiringHoursSample1() {
        return new HiringHours().id(1L).hour(1);
    }

    public static HiringHours getHiringHoursSample2() {
        return new HiringHours().id(2L).hour(2);
    }

    public static HiringHours getHiringHoursRandomSampleGenerator() {
        return new HiringHours().id(longCount.incrementAndGet()).hour(intCount.incrementAndGet());
    }
}
