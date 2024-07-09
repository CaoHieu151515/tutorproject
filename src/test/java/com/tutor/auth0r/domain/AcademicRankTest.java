package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AcademicRankTestSamples.*;
import static com.tutor.auth0r.domain.MediaTestSamples.*;
import static com.tutor.auth0r.domain.UserVerifyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicRankTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicRank.class);
        AcademicRank academicRank1 = getAcademicRankSample1();
        AcademicRank academicRank2 = new AcademicRank();
        assertThat(academicRank1).isNotEqualTo(academicRank2);

        academicRank2.setId(academicRank1.getId());
        assertThat(academicRank1).isEqualTo(academicRank2);

        academicRank2 = getAcademicRankSample2();
        assertThat(academicRank1).isNotEqualTo(academicRank2);
    }

    @Test
    void mediaTest() {
        AcademicRank academicRank = getAcademicRankRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        academicRank.setMedia(mediaBack);
        assertThat(academicRank.getMedia()).isEqualTo(mediaBack);

        academicRank.media(null);
        assertThat(academicRank.getMedia()).isNull();
    }

    @Test
    void userVerifyTest() {
        AcademicRank academicRank = getAcademicRankRandomSampleGenerator();
        UserVerify userVerifyBack = getUserVerifyRandomSampleGenerator();

        academicRank.setUserVerify(userVerifyBack);
        assertThat(academicRank.getUserVerify()).isEqualTo(userVerifyBack);

        academicRank.userVerify(null);
        assertThat(academicRank.getUserVerify()).isNull();
    }
}
