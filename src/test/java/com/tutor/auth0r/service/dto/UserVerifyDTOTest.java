package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserVerifyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserVerifyDTO.class);
        UserVerifyDTO userVerifyDTO1 = new UserVerifyDTO();
        userVerifyDTO1.setId(1L);
        UserVerifyDTO userVerifyDTO2 = new UserVerifyDTO();
        assertThat(userVerifyDTO1).isNotEqualTo(userVerifyDTO2);
        userVerifyDTO2.setId(userVerifyDTO1.getId());
        assertThat(userVerifyDTO1).isEqualTo(userVerifyDTO2);
        userVerifyDTO2.setId(2L);
        assertThat(userVerifyDTO1).isNotEqualTo(userVerifyDTO2);
        userVerifyDTO1.setId(null);
        assertThat(userVerifyDTO1).isNotEqualTo(userVerifyDTO2);
    }
}
