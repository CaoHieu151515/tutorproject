package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TutorVideoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TutorVideo getTutorVideoSample1() {
        return new TutorVideo().id(1L);
    }

    public static TutorVideo getTutorVideoSample2() {
        return new TutorVideo().id(2L);
    }

    public static TutorVideo getTutorVideoRandomSampleGenerator() {
        return new TutorVideo().id(longCount.incrementAndGet());
    }
}
