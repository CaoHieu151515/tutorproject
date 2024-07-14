package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThirdPartyTransactionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdPartyTransactionDTO.class);
        ThirdPartyTransactionDTO thirdPartyTransactionDTO1 = new ThirdPartyTransactionDTO();
        thirdPartyTransactionDTO1.setId(1L);
        ThirdPartyTransactionDTO thirdPartyTransactionDTO2 = new ThirdPartyTransactionDTO();
        assertThat(thirdPartyTransactionDTO1).isNotEqualTo(thirdPartyTransactionDTO2);
        thirdPartyTransactionDTO2.setId(thirdPartyTransactionDTO1.getId());
        assertThat(thirdPartyTransactionDTO1).isEqualTo(thirdPartyTransactionDTO2);
        thirdPartyTransactionDTO2.setId(2L);
        assertThat(thirdPartyTransactionDTO1).isNotEqualTo(thirdPartyTransactionDTO2);
        thirdPartyTransactionDTO1.setId(null);
        assertThat(thirdPartyTransactionDTO1).isNotEqualTo(thirdPartyTransactionDTO2);
    }
}
