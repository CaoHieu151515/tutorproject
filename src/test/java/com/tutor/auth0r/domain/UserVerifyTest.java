package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AcademicRankTestSamples.*;
import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.IdentityCardTestSamples.*;
import static com.tutor.auth0r.domain.UserVerifyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UserVerifyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserVerify.class);
        UserVerify userVerify1 = getUserVerifySample1();
        UserVerify userVerify2 = new UserVerify();
        assertThat(userVerify1).isNotEqualTo(userVerify2);

        userVerify2.setId(userVerify1.getId());
        assertThat(userVerify1).isEqualTo(userVerify2);

        userVerify2 = getUserVerifySample2();
        assertThat(userVerify1).isNotEqualTo(userVerify2);
    }

    @Test
    void identityCardTest() throws Exception {
        UserVerify userVerify = getUserVerifyRandomSampleGenerator();
        IdentityCard identityCardBack = getIdentityCardRandomSampleGenerator();

        userVerify.setIdentityCard(identityCardBack);
        assertThat(userVerify.getIdentityCard()).isEqualTo(identityCardBack);

        userVerify.identityCard(null);
        assertThat(userVerify.getIdentityCard()).isNull();
    }

    @Test
    void academicRankTest() throws Exception {
        UserVerify userVerify = getUserVerifyRandomSampleGenerator();
        AcademicRank academicRankBack = getAcademicRankRandomSampleGenerator();

        userVerify.addAcademicRank(academicRankBack);
        assertThat(userVerify.getAcademicRanks()).containsOnly(academicRankBack);
        assertThat(academicRankBack.getUserVerify()).isEqualTo(userVerify);

        userVerify.removeAcademicRank(academicRankBack);
        assertThat(userVerify.getAcademicRanks()).doesNotContain(academicRankBack);
        assertThat(academicRankBack.getUserVerify()).isNull();

        userVerify.academicRanks(new HashSet<>(Set.of(academicRankBack)));
        assertThat(userVerify.getAcademicRanks()).containsOnly(academicRankBack);
        assertThat(academicRankBack.getUserVerify()).isEqualTo(userVerify);

        userVerify.setAcademicRanks(new HashSet<>());
        assertThat(userVerify.getAcademicRanks()).doesNotContain(academicRankBack);
        assertThat(academicRankBack.getUserVerify()).isNull();
    }

    @Test
    void appUserTest() throws Exception {
        UserVerify userVerify = getUserVerifyRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        userVerify.setAppUser(appUserBack);
        assertThat(userVerify.getAppUser()).isEqualTo(appUserBack);
        assertThat(appUserBack.getUserVerify()).isEqualTo(userVerify);

        userVerify.appUser(null);
        assertThat(userVerify.getAppUser()).isNull();
        assertThat(appUserBack.getUserVerify()).isNull();
    }
}
