package com.tutor.auth0r.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UserVerifyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UserVerify getUserVerifySample1() {
        return new UserVerify().id(1L).rating(1L).school("school1").studentID("studentID1").major("major1").graduationYear(1L);
    }

    public static UserVerify getUserVerifySample2() {
        return new UserVerify().id(2L).rating(2L).school("school2").studentID("studentID2").major("major2").graduationYear(2L);
    }

    public static UserVerify getUserVerifyRandomSampleGenerator() {
        return new UserVerify()
            .id(longCount.incrementAndGet())
            .rating(longCount.incrementAndGet())
            .school(UUID.randomUUID().toString())
            .studentID(UUID.randomUUID().toString())
            .major(UUID.randomUUID().toString())
            .graduationYear(longCount.incrementAndGet());
    }
}
