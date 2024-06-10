package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdentityCardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentityCardDTO.class);
        IdentityCardDTO identityCardDTO1 = new IdentityCardDTO();
        identityCardDTO1.setId(1L);
        IdentityCardDTO identityCardDTO2 = new IdentityCardDTO();
        assertThat(identityCardDTO1).isNotEqualTo(identityCardDTO2);
        identityCardDTO2.setId(identityCardDTO1.getId());
        assertThat(identityCardDTO1).isEqualTo(identityCardDTO2);
        identityCardDTO2.setId(2L);
        assertThat(identityCardDTO1).isNotEqualTo(identityCardDTO2);
        identityCardDTO1.setId(null);
        assertThat(identityCardDTO1).isNotEqualTo(identityCardDTO2);
    }
}
