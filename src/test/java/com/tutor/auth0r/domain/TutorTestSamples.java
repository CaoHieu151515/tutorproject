package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TutorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tutor getTutorSample1() {
        return new Tutor().id(1L).followerCount(1L);
    }

    public static Tutor getTutorSample2() {
        return new Tutor().id(2L).followerCount(2L);
    }

    public static Tutor getTutorRandomSampleGenerator() {
        return new Tutor().id(longCount.incrementAndGet()).followerCount(longCount.incrementAndGet());
    }
}
