package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.MediaTestSamples.*;
import static com.tutor.auth0r.domain.TutorDetailsTestSamples.*;
import static com.tutor.auth0r.domain.TutorVideoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorVideoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorVideo.class);
        TutorVideo tutorVideo1 = getTutorVideoSample1();
        TutorVideo tutorVideo2 = new TutorVideo();
        assertThat(tutorVideo1).isNotEqualTo(tutorVideo2);

        tutorVideo2.setId(tutorVideo1.getId());
        assertThat(tutorVideo1).isEqualTo(tutorVideo2);

        tutorVideo2 = getTutorVideoSample2();
        assertThat(tutorVideo1).isNotEqualTo(tutorVideo2);
    }

    @Test
    void mediaTest() throws Exception {
        TutorVideo tutorVideo = getTutorVideoRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        tutorVideo.setMedia(mediaBack);
        assertThat(tutorVideo.getMedia()).isEqualTo(mediaBack);

        tutorVideo.media(null);
        assertThat(tutorVideo.getMedia()).isNull();
    }

    @Test
    void tutorDetailsTest() throws Exception {
        TutorVideo tutorVideo = getTutorVideoRandomSampleGenerator();
        TutorDetails tutorDetailsBack = getTutorDetailsRandomSampleGenerator();

        tutorVideo.setTutorDetails(tutorDetailsBack);
        assertThat(tutorVideo.getTutorDetails()).isEqualTo(tutorDetailsBack);
        assertThat(tutorDetailsBack.getTutorVideo()).isEqualTo(tutorVideo);

        tutorVideo.tutorDetails(null);
        assertThat(tutorVideo.getTutorDetails()).isNull();
        assertThat(tutorDetailsBack.getTutorVideo()).isNull();
    }
}
