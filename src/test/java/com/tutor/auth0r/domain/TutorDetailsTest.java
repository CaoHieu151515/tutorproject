package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.TutorDetailsTestSamples.*;
import static com.tutor.auth0r.domain.TutorImageTestSamples.*;
import static com.tutor.auth0r.domain.TutorTeachTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static com.tutor.auth0r.domain.TutorVideoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TutorDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorDetails.class);
        TutorDetails tutorDetails1 = getTutorDetailsSample1();
        TutorDetails tutorDetails2 = new TutorDetails();
        assertThat(tutorDetails1).isNotEqualTo(tutorDetails2);

        tutorDetails2.setId(tutorDetails1.getId());
        assertThat(tutorDetails1).isEqualTo(tutorDetails2);

        tutorDetails2 = getTutorDetailsSample2();
        assertThat(tutorDetails1).isNotEqualTo(tutorDetails2);
    }

    @Test
    void tutorVideoTest() throws Exception {
        TutorDetails tutorDetails = getTutorDetailsRandomSampleGenerator();
        TutorVideo tutorVideoBack = getTutorVideoRandomSampleGenerator();

        tutorDetails.setTutorVideo(tutorVideoBack);
        assertThat(tutorDetails.getTutorVideo()).isEqualTo(tutorVideoBack);

        tutorDetails.tutorVideo(null);
        assertThat(tutorDetails.getTutorVideo()).isNull();
    }

    @Test
    void tutorTeachTest() throws Exception {
        TutorDetails tutorDetails = getTutorDetailsRandomSampleGenerator();
        TutorTeach tutorTeachBack = getTutorTeachRandomSampleGenerator();

        tutorDetails.addTutorTeach(tutorTeachBack);
        assertThat(tutorDetails.getTutorTeaches()).containsOnly(tutorTeachBack);
        assertThat(tutorTeachBack.getTutorDetails()).isEqualTo(tutorDetails);

        tutorDetails.removeTutorTeach(tutorTeachBack);
        assertThat(tutorDetails.getTutorTeaches()).doesNotContain(tutorTeachBack);
        assertThat(tutorTeachBack.getTutorDetails()).isNull();

        tutorDetails.tutorTeaches(new HashSet<>(Set.of(tutorTeachBack)));
        assertThat(tutorDetails.getTutorTeaches()).containsOnly(tutorTeachBack);
        assertThat(tutorTeachBack.getTutorDetails()).isEqualTo(tutorDetails);

        tutorDetails.setTutorTeaches(new HashSet<>());
        assertThat(tutorDetails.getTutorTeaches()).doesNotContain(tutorTeachBack);
        assertThat(tutorTeachBack.getTutorDetails()).isNull();
    }

    @Test
    void tutorImageTest() throws Exception {
        TutorDetails tutorDetails = getTutorDetailsRandomSampleGenerator();
        TutorImage tutorImageBack = getTutorImageRandomSampleGenerator();

        tutorDetails.addTutorImage(tutorImageBack);
        assertThat(tutorDetails.getTutorImages()).containsOnly(tutorImageBack);
        assertThat(tutorImageBack.getTutorDetails()).isEqualTo(tutorDetails);

        tutorDetails.removeTutorImage(tutorImageBack);
        assertThat(tutorDetails.getTutorImages()).doesNotContain(tutorImageBack);
        assertThat(tutorImageBack.getTutorDetails()).isNull();

        tutorDetails.tutorImages(new HashSet<>(Set.of(tutorImageBack)));
        assertThat(tutorDetails.getTutorImages()).containsOnly(tutorImageBack);
        assertThat(tutorImageBack.getTutorDetails()).isEqualTo(tutorDetails);

        tutorDetails.setTutorImages(new HashSet<>());
        assertThat(tutorDetails.getTutorImages()).doesNotContain(tutorImageBack);
        assertThat(tutorImageBack.getTutorDetails()).isNull();
    }

    @Test
    void tutorTest() throws Exception {
        TutorDetails tutorDetails = getTutorDetailsRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        tutorDetails.setTutor(tutorBack);
        assertThat(tutorDetails.getTutor()).isEqualTo(tutorBack);
        assertThat(tutorBack.getTutorDetails()).isEqualTo(tutorDetails);

        tutorDetails.tutor(null);
        assertThat(tutorDetails.getTutor()).isNull();
        assertThat(tutorBack.getTutorDetails()).isNull();
    }
}
