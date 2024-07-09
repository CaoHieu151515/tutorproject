package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.HireTutorTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HireTutorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HireTutor.class);
        HireTutor hireTutor1 = getHireTutorSample1();
        HireTutor hireTutor2 = new HireTutor();
        assertThat(hireTutor1).isNotEqualTo(hireTutor2);

        hireTutor2.setId(hireTutor1.getId());
        assertThat(hireTutor1).isEqualTo(hireTutor2);

        hireTutor2 = getHireTutorSample2();
        assertThat(hireTutor1).isNotEqualTo(hireTutor2);
    }

    @Test
    void appUserTest() {
        HireTutor hireTutor = getHireTutorRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        hireTutor.setAppUser(appUserBack);
        assertThat(hireTutor.getAppUser()).isEqualTo(appUserBack);

        hireTutor.appUser(null);
        assertThat(hireTutor.getAppUser()).isNull();
    }

    @Test
    void tutorTest() {
        HireTutor hireTutor = getHireTutorRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        hireTutor.setTutor(tutorBack);
        assertThat(hireTutor.getTutor()).isEqualTo(tutorBack);

        hireTutor.tutor(null);
        assertThat(hireTutor.getTutor()).isNull();
    }
}
