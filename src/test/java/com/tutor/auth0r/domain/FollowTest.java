package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.FollowTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FollowTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follow.class);
        Follow follow1 = getFollowSample1();
        Follow follow2 = new Follow();
        assertThat(follow1).isNotEqualTo(follow2);

        follow2.setId(follow1.getId());
        assertThat(follow1).isEqualTo(follow2);

        follow2 = getFollowSample2();
        assertThat(follow1).isNotEqualTo(follow2);
    }

    @Test
    void followerAppUserTest() throws Exception {
        Follow follow = getFollowRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        follow.setFollowerAppUser(appUserBack);
        assertThat(follow.getFollowerAppUser()).isEqualTo(appUserBack);

        follow.followerAppUser(null);
        assertThat(follow.getFollowerAppUser()).isNull();
    }

    @Test
    void followedTutorTest() throws Exception {
        Follow follow = getFollowRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        follow.setFollowedTutor(tutorBack);
        assertThat(follow.getFollowedTutor()).isEqualTo(tutorBack);

        follow.followedTutor(null);
        assertThat(follow.getFollowedTutor()).isNull();
    }
}
