package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AcademicRankTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AcademicRank getAcademicRankSample1() {
        return new AcademicRank().id(1L);
    }

    public static AcademicRank getAcademicRankSample2() {
        return new AcademicRank().id(2L);
    }

    public static AcademicRank getAcademicRankRandomSampleGenerator() {
        return new AcademicRank().id(longCount.incrementAndGet());
    }
}
