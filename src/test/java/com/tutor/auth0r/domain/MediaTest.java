package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AcademicRankTestSamples.*;
import static com.tutor.auth0r.domain.IdentityCardTestSamples.*;
import static com.tutor.auth0r.domain.MediaTestSamples.*;
import static com.tutor.auth0r.domain.TutorImageTestSamples.*;
import static com.tutor.auth0r.domain.TutorVideoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Media.class);
        Media media1 = getMediaSample1();
        Media media2 = new Media();
        assertThat(media1).isNotEqualTo(media2);

        media2.setId(media1.getId());
        assertThat(media1).isEqualTo(media2);

        media2 = getMediaSample2();
        assertThat(media1).isNotEqualTo(media2);
    }

    @Test
    void academicRankTest() {
        Media media = getMediaRandomSampleGenerator();
        AcademicRank academicRankBack = getAcademicRankRandomSampleGenerator();

        media.setAcademicRank(academicRankBack);
        assertThat(media.getAcademicRank()).isEqualTo(academicRankBack);
        assertThat(academicRankBack.getMedia()).isEqualTo(media);

        media.academicRank(null);
        assertThat(media.getAcademicRank()).isNull();
        assertThat(academicRankBack.getMedia()).isNull();
    }

    @Test
    void identityCardTest() {
        Media media = getMediaRandomSampleGenerator();
        IdentityCard identityCardBack = getIdentityCardRandomSampleGenerator();

        media.setIdentityCard(identityCardBack);
        assertThat(media.getIdentityCard()).isEqualTo(identityCardBack);

        media.identityCard(null);
        assertThat(media.getIdentityCard()).isNull();
    }

    @Test
    void tutorImageTest() {
        Media media = getMediaRandomSampleGenerator();
        TutorImage tutorImageBack = getTutorImageRandomSampleGenerator();

        media.setTutorImage(tutorImageBack);
        assertThat(media.getTutorImage()).isEqualTo(tutorImageBack);
        assertThat(tutorImageBack.getMedia()).isEqualTo(media);

        media.tutorImage(null);
        assertThat(media.getTutorImage()).isNull();
        assertThat(tutorImageBack.getMedia()).isNull();
    }

    @Test
    void tutorVideoTest() {
        Media media = getMediaRandomSampleGenerator();
        TutorVideo tutorVideoBack = getTutorVideoRandomSampleGenerator();

        media.setTutorVideo(tutorVideoBack);
        assertThat(media.getTutorVideo()).isEqualTo(tutorVideoBack);
        assertThat(tutorVideoBack.getMedia()).isEqualTo(media);

        media.tutorVideo(null);
        assertThat(media.getTutorVideo()).isNull();
        assertThat(tutorVideoBack.getMedia()).isNull();
    }
}
