package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorDTO.class);
        TutorDTO tutorDTO1 = new TutorDTO();
        tutorDTO1.setId(1L);
        TutorDTO tutorDTO2 = new TutorDTO();
        assertThat(tutorDTO1).isNotEqualTo(tutorDTO2);
        tutorDTO2.setId(tutorDTO1.getId());
        assertThat(tutorDTO1).isEqualTo(tutorDTO2);
        tutorDTO2.setId(2L);
        assertThat(tutorDTO1).isNotEqualTo(tutorDTO2);
        tutorDTO1.setId(null);
        assertThat(tutorDTO1).isNotEqualTo(tutorDTO2);
    }
}
