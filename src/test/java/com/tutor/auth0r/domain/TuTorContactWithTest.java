package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.TuTorContactWithTestSamples.*;
import static com.tutor.auth0r.domain.TutorDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TuTorContactWithTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TuTorContactWith.class);
        TuTorContactWith tuTorContactWith1 = getTuTorContactWithSample1();
        TuTorContactWith tuTorContactWith2 = new TuTorContactWith();
        assertThat(tuTorContactWith1).isNotEqualTo(tuTorContactWith2);

        tuTorContactWith2.setId(tuTorContactWith1.getId());
        assertThat(tuTorContactWith1).isEqualTo(tuTorContactWith2);

        tuTorContactWith2 = getTuTorContactWithSample2();
        assertThat(tuTorContactWith1).isNotEqualTo(tuTorContactWith2);
    }

    @Test
    void tutorDetailsTest() {
        TuTorContactWith tuTorContactWith = getTuTorContactWithRandomSampleGenerator();
        TutorDetails tutorDetailsBack = getTutorDetailsRandomSampleGenerator();

        tuTorContactWith.setTutorDetails(tutorDetailsBack);
        assertThat(tuTorContactWith.getTutorDetails()).isEqualTo(tutorDetailsBack);

        tuTorContactWith.tutorDetails(null);
        assertThat(tuTorContactWith.getTutorDetails()).isNull();
    }
}
