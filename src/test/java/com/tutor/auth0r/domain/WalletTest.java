package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.WalletTestSamples.*;
import static com.tutor.auth0r.domain.WalletTransactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WalletTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wallet.class);
        Wallet wallet1 = getWalletSample1();
        Wallet wallet2 = new Wallet();
        assertThat(wallet1).isNotEqualTo(wallet2);

        wallet2.setId(wallet1.getId());
        assertThat(wallet1).isEqualTo(wallet2);

        wallet2 = getWalletSample2();
        assertThat(wallet1).isNotEqualTo(wallet2);
    }

    @Test
    void appUserTest() {
        Wallet wallet = getWalletRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        wallet.setAppUser(appUserBack);
        assertThat(wallet.getAppUser()).isEqualTo(appUserBack);

        wallet.appUser(null);
        assertThat(wallet.getAppUser()).isNull();
    }

    @Test
    void transactionsTest() {
        Wallet wallet = getWalletRandomSampleGenerator();
        WalletTransaction walletTransactionBack = getWalletTransactionRandomSampleGenerator();

        wallet.addTransactions(walletTransactionBack);
        assertThat(wallet.getTransactions()).containsOnly(walletTransactionBack);
        assertThat(walletTransactionBack.getWallet()).isEqualTo(wallet);

        wallet.removeTransactions(walletTransactionBack);
        assertThat(wallet.getTransactions()).doesNotContain(walletTransactionBack);
        assertThat(walletTransactionBack.getWallet()).isNull();

        wallet.transactions(new HashSet<>(Set.of(walletTransactionBack)));
        assertThat(wallet.getTransactions()).containsOnly(walletTransactionBack);
        assertThat(walletTransactionBack.getWallet()).isEqualTo(wallet);

        wallet.setTransactions(new HashSet<>());
        assertThat(wallet.getTransactions()).doesNotContain(walletTransactionBack);
        assertThat(walletTransactionBack.getWallet()).isNull();
    }
}
