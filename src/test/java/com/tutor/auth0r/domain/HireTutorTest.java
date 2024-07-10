package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.HireTutorTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static com.tutor.auth0r.domain.WalletTransactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HireTutorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HireTutor.class);
        HireTutor hireTutor1 = getHireTutorSample1();
        HireTutor hireTutor2 = new HireTutor();
        assertThat(hireTutor1).isNotEqualTo(hireTutor2);

        hireTutor2.setId(hireTutor1.getId());
        assertThat(hireTutor1).isEqualTo(hireTutor2);

        hireTutor2 = getHireTutorSample2();
        assertThat(hireTutor1).isNotEqualTo(hireTutor2);
    }

    @Test
    void walletTransactionTest() {
        HireTutor hireTutor = getHireTutorRandomSampleGenerator();
        WalletTransaction walletTransactionBack = getWalletTransactionRandomSampleGenerator();

        hireTutor.addWalletTransaction(walletTransactionBack);
        assertThat(hireTutor.getWalletTransactions()).containsOnly(walletTransactionBack);
        assertThat(walletTransactionBack.getHireTutor()).isEqualTo(hireTutor);

        hireTutor.removeWalletTransaction(walletTransactionBack);
        assertThat(hireTutor.getWalletTransactions()).doesNotContain(walletTransactionBack);
        assertThat(walletTransactionBack.getHireTutor()).isNull();

        hireTutor.walletTransactions(new HashSet<>(Set.of(walletTransactionBack)));
        assertThat(hireTutor.getWalletTransactions()).containsOnly(walletTransactionBack);
        assertThat(walletTransactionBack.getHireTutor()).isEqualTo(hireTutor);

        hireTutor.setWalletTransactions(new HashSet<>());
        assertThat(hireTutor.getWalletTransactions()).doesNotContain(walletTransactionBack);
        assertThat(walletTransactionBack.getHireTutor()).isNull();
    }

    @Test
    void appUserTest() {
        HireTutor hireTutor = getHireTutorRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        hireTutor.setAppUser(appUserBack);
        assertThat(hireTutor.getAppUser()).isEqualTo(appUserBack);

        hireTutor.appUser(null);
        assertThat(hireTutor.getAppUser()).isNull();
    }

    @Test
    void tutorTest() {
        HireTutor hireTutor = getHireTutorRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        hireTutor.setTutor(tutorBack);
        assertThat(hireTutor.getTutor()).isEqualTo(tutorBack);

        hireTutor.tutor(null);
        assertThat(hireTutor.getTutor()).isNull();
    }
}
