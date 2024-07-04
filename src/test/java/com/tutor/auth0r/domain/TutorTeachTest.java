package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.TutorDetailsTestSamples.*;
import static com.tutor.auth0r.domain.TutorTeachTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorTeachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorTeach.class);
        TutorTeach tutorTeach1 = getTutorTeachSample1();
        TutorTeach tutorTeach2 = new TutorTeach();
        assertThat(tutorTeach1).isNotEqualTo(tutorTeach2);

        tutorTeach2.setId(tutorTeach1.getId());
        assertThat(tutorTeach1).isEqualTo(tutorTeach2);

        tutorTeach2 = getTutorTeachSample2();
        assertThat(tutorTeach1).isNotEqualTo(tutorTeach2);
    }

    @Test
    void tutorDetailsTest() throws Exception {
        TutorTeach tutorTeach = getTutorTeachRandomSampleGenerator();
        TutorDetails tutorDetailsBack = getTutorDetailsRandomSampleGenerator();

        tutorTeach.setTutorDetails(tutorDetailsBack);
        assertThat(tutorTeach.getTutorDetails()).isEqualTo(tutorDetailsBack);

        tutorTeach.tutorDetails(null);
        assertThat(tutorTeach.getTutorDetails()).isNull();
    }
}
