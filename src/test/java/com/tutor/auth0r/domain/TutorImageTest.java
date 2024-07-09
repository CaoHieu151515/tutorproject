package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.MediaTestSamples.*;
import static com.tutor.auth0r.domain.TutorDetailsTestSamples.*;
import static com.tutor.auth0r.domain.TutorImageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorImage.class);
        TutorImage tutorImage1 = getTutorImageSample1();
        TutorImage tutorImage2 = new TutorImage();
        assertThat(tutorImage1).isNotEqualTo(tutorImage2);

        tutorImage2.setId(tutorImage1.getId());
        assertThat(tutorImage1).isEqualTo(tutorImage2);

        tutorImage2 = getTutorImageSample2();
        assertThat(tutorImage1).isNotEqualTo(tutorImage2);
    }

    @Test
    void mediaTest() {
        TutorImage tutorImage = getTutorImageRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        tutorImage.setMedia(mediaBack);
        assertThat(tutorImage.getMedia()).isEqualTo(mediaBack);

        tutorImage.media(null);
        assertThat(tutorImage.getMedia()).isNull();
    }

    @Test
    void tutorDetailsTest() {
        TutorImage tutorImage = getTutorImageRandomSampleGenerator();
        TutorDetails tutorDetailsBack = getTutorDetailsRandomSampleGenerator();

        tutorImage.setTutorDetails(tutorDetailsBack);
        assertThat(tutorImage.getTutorDetails()).isEqualTo(tutorDetailsBack);

        tutorImage.tutorDetails(null);
        assertThat(tutorImage.getTutorDetails()).isNull();
    }
}
