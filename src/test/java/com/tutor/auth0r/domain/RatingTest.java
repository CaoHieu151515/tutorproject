package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.RatingTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RatingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rating.class);
        Rating rating1 = getRatingSample1();
        Rating rating2 = new Rating();
        assertThat(rating1).isNotEqualTo(rating2);

        rating2.setId(rating1.getId());
        assertThat(rating1).isEqualTo(rating2);

        rating2 = getRatingSample2();
        assertThat(rating1).isNotEqualTo(rating2);
    }

    @Test
    void tutorTest() {
        Rating rating = getRatingRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        rating.setTutor(tutorBack);
        assertThat(rating.getTutor()).isEqualTo(tutorBack);

        rating.tutor(null);
        assertThat(rating.getTutor()).isNull();
    }

    @Test
    void appUserTest() {
        Rating rating = getRatingRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        rating.setAppUser(appUserBack);
        assertThat(rating.getAppUser()).isEqualTo(appUserBack);

        rating.appUser(null);
        assertThat(rating.getAppUser()).isNull();
    }
}
