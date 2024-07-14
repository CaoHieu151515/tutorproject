package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.ThirdPartyTransactionTestSamples.*;
import static com.tutor.auth0r.domain.WalletTransactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThirdPartyTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdPartyTransaction.class);
        ThirdPartyTransaction thirdPartyTransaction1 = getThirdPartyTransactionSample1();
        ThirdPartyTransaction thirdPartyTransaction2 = new ThirdPartyTransaction();
        assertThat(thirdPartyTransaction1).isNotEqualTo(thirdPartyTransaction2);

        thirdPartyTransaction2.setId(thirdPartyTransaction1.getId());
        assertThat(thirdPartyTransaction1).isEqualTo(thirdPartyTransaction2);

        thirdPartyTransaction2 = getThirdPartyTransactionSample2();
        assertThat(thirdPartyTransaction1).isNotEqualTo(thirdPartyTransaction2);
    }

    @Test
    void walletTransactionTest() {
        ThirdPartyTransaction thirdPartyTransaction = getThirdPartyTransactionRandomSampleGenerator();
        WalletTransaction walletTransactionBack = getWalletTransactionRandomSampleGenerator();

        thirdPartyTransaction.setWalletTransaction(walletTransactionBack);
        assertThat(thirdPartyTransaction.getWalletTransaction()).isEqualTo(walletTransactionBack);

        thirdPartyTransaction.walletTransaction(null);
        assertThat(thirdPartyTransaction.getWalletTransaction()).isNull();
    }
}
