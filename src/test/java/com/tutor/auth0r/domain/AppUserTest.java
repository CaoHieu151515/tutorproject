package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.HireTutorTestSamples.*;
import static com.tutor.auth0r.domain.RatingTestSamples.*;
import static com.tutor.auth0r.domain.ReportTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static com.tutor.auth0r.domain.UserVerifyTestSamples.*;
import static com.tutor.auth0r.domain.WalletTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void tutorTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        appUser.setTutor(tutorBack);
        assertThat(appUser.getTutor()).isEqualTo(tutorBack);

        appUser.tutor(null);
        assertThat(appUser.getTutor()).isNull();
    }

    @Test
    void userVerifyTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        UserVerify userVerifyBack = getUserVerifyRandomSampleGenerator();

        appUser.setUserVerify(userVerifyBack);
        assertThat(appUser.getUserVerify()).isEqualTo(userVerifyBack);

        appUser.userVerify(null);
        assertThat(appUser.getUserVerify()).isNull();
    }

    @Test
    void hireTutorTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        HireTutor hireTutorBack = getHireTutorRandomSampleGenerator();

        appUser.addHireTutor(hireTutorBack);
        assertThat(appUser.getHireTutors()).containsOnly(hireTutorBack);
        assertThat(hireTutorBack.getAppUser()).isEqualTo(appUser);

        appUser.removeHireTutor(hireTutorBack);
        assertThat(appUser.getHireTutors()).doesNotContain(hireTutorBack);
        assertThat(hireTutorBack.getAppUser()).isNull();

        appUser.hireTutors(new HashSet<>(Set.of(hireTutorBack)));
        assertThat(appUser.getHireTutors()).containsOnly(hireTutorBack);
        assertThat(hireTutorBack.getAppUser()).isEqualTo(appUser);

        appUser.setHireTutors(new HashSet<>());
        assertThat(appUser.getHireTutors()).doesNotContain(hireTutorBack);
        assertThat(hireTutorBack.getAppUser()).isNull();
    }

    @Test
    void reportTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Report reportBack = getReportRandomSampleGenerator();

        appUser.addReport(reportBack);
        assertThat(appUser.getReports()).containsOnly(reportBack);
        assertThat(reportBack.getAppUser()).isEqualTo(appUser);

        appUser.removeReport(reportBack);
        assertThat(appUser.getReports()).doesNotContain(reportBack);
        assertThat(reportBack.getAppUser()).isNull();

        appUser.reports(new HashSet<>(Set.of(reportBack)));
        assertThat(appUser.getReports()).containsOnly(reportBack);
        assertThat(reportBack.getAppUser()).isEqualTo(appUser);

        appUser.setReports(new HashSet<>());
        assertThat(appUser.getReports()).doesNotContain(reportBack);
        assertThat(reportBack.getAppUser()).isNull();
    }

    @Test
    void walletTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Wallet walletBack = getWalletRandomSampleGenerator();

        appUser.setWallet(walletBack);
        assertThat(appUser.getWallet()).isEqualTo(walletBack);
        assertThat(walletBack.getAppUser()).isEqualTo(appUser);

        appUser.wallet(null);
        assertThat(appUser.getWallet()).isNull();
        assertThat(walletBack.getAppUser()).isNull();
    }

    @Test
    void ratingTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Rating ratingBack = getRatingRandomSampleGenerator();

        appUser.addRating(ratingBack);
        assertThat(appUser.getRatings()).containsOnly(ratingBack);
        assertThat(ratingBack.getAppUser()).isEqualTo(appUser);

        appUser.removeRating(ratingBack);
        assertThat(appUser.getRatings()).doesNotContain(ratingBack);
        assertThat(ratingBack.getAppUser()).isNull();

        appUser.ratings(new HashSet<>(Set.of(ratingBack)));
        assertThat(appUser.getRatings()).containsOnly(ratingBack);
        assertThat(ratingBack.getAppUser()).isEqualTo(appUser);

        appUser.setRatings(new HashSet<>());
        assertThat(appUser.getRatings()).doesNotContain(ratingBack);
        assertThat(ratingBack.getAppUser()).isNull();
    }
}
