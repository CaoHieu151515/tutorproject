package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HireTutorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HireTutorDTO.class);
        HireTutorDTO hireTutorDTO1 = new HireTutorDTO();
        hireTutorDTO1.setId(1L);
        HireTutorDTO hireTutorDTO2 = new HireTutorDTO();
        assertThat(hireTutorDTO1).isNotEqualTo(hireTutorDTO2);
        hireTutorDTO2.setId(hireTutorDTO1.getId());
        assertThat(hireTutorDTO1).isEqualTo(hireTutorDTO2);
        hireTutorDTO2.setId(2L);
        assertThat(hireTutorDTO1).isNotEqualTo(hireTutorDTO2);
        hireTutorDTO1.setId(null);
        assertThat(hireTutorDTO1).isNotEqualTo(hireTutorDTO2);
    }
}
