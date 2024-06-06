package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TutorTeachTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TutorTeach getTutorTeachSample1() {
        return new TutorTeach().id(1L);
    }

    public static TutorTeach getTutorTeachSample2() {
        return new TutorTeach().id(2L);
    }

    public static TutorTeach getTutorTeachRandomSampleGenerator() {
        return new TutorTeach().id(longCount.incrementAndGet());
    }
}
