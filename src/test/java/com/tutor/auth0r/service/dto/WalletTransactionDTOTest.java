package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WalletTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletTransactionDTO.class);
        WalletTransactionDTO walletTransactionDTO1 = new WalletTransactionDTO();
        walletTransactionDTO1.setId(1L);
        WalletTransactionDTO walletTransactionDTO2 = new WalletTransactionDTO();
        assertThat(walletTransactionDTO1).isNotEqualTo(walletTransactionDTO2);
        walletTransactionDTO2.setId(walletTransactionDTO1.getId());
        assertThat(walletTransactionDTO1).isEqualTo(walletTransactionDTO2);
        walletTransactionDTO2.setId(2L);
        assertThat(walletTransactionDTO1).isNotEqualTo(walletTransactionDTO2);
        walletTransactionDTO1.setId(null);
        assertThat(walletTransactionDTO1).isNotEqualTo(walletTransactionDTO2);
    }
}
