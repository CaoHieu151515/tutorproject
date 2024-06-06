package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TutorDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TutorDetails getTutorDetailsSample1() {
        return new TutorDetails().id(1L).information("information1");
    }

    public static TutorDetails getTutorDetailsSample2() {
        return new TutorDetails().id(2L).information("information2");
    }

    public static TutorDetails getTutorDetailsRandomSampleGenerator() {
        return new TutorDetails().id(longCount.incrementAndGet()).information(UUID.randomUUID().toString());
    }
}
