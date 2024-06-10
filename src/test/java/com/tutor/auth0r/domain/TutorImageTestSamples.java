package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TutorImageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TutorImage getTutorImageSample1() {
        return new TutorImage().id(1L);
    }

    public static TutorImage getTutorImageSample2() {
        return new TutorImage().id(2L);
    }

    public static TutorImage getTutorImageRandomSampleGenerator() {
        return new TutorImage().id(longCount.incrementAndGet());
    }
}
