package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.WalletTestSamples.*;
import static com.tutor.auth0r.domain.WalletTransactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WalletTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletTransaction.class);
        WalletTransaction walletTransaction1 = getWalletTransactionSample1();
        WalletTransaction walletTransaction2 = new WalletTransaction();
        assertThat(walletTransaction1).isNotEqualTo(walletTransaction2);

        walletTransaction2.setId(walletTransaction1.getId());
        assertThat(walletTransaction1).isEqualTo(walletTransaction2);

        walletTransaction2 = getWalletTransactionSample2();
        assertThat(walletTransaction1).isNotEqualTo(walletTransaction2);
    }

    @Test
    void walletTest() throws Exception {
        WalletTransaction walletTransaction = getWalletTransactionRandomSampleGenerator();
        Wallet walletBack = getWalletRandomSampleGenerator();

        walletTransaction.setWallet(walletBack);
        assertThat(walletTransaction.getWallet()).isEqualTo(walletBack);

        walletTransaction.wallet(null);
        assertThat(walletTransaction.getWallet()).isNull();
    }
}
