package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TuTorContactWithDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TuTorContactWithDTO.class);
        TuTorContactWithDTO tuTorContactWithDTO1 = new TuTorContactWithDTO();
        tuTorContactWithDTO1.setId(1L);
        TuTorContactWithDTO tuTorContactWithDTO2 = new TuTorContactWithDTO();
        assertThat(tuTorContactWithDTO1).isNotEqualTo(tuTorContactWithDTO2);
        tuTorContactWithDTO2.setId(tuTorContactWithDTO1.getId());
        assertThat(tuTorContactWithDTO1).isEqualTo(tuTorContactWithDTO2);
        tuTorContactWithDTO2.setId(2L);
        assertThat(tuTorContactWithDTO1).isNotEqualTo(tuTorContactWithDTO2);
        tuTorContactWithDTO1.setId(null);
        assertThat(tuTorContactWithDTO1).isNotEqualTo(tuTorContactWithDTO2);
    }
}
