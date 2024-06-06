package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorTeachDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorTeachDTO.class);
        TutorTeachDTO tutorTeachDTO1 = new TutorTeachDTO();
        tutorTeachDTO1.setId(1L);
        TutorTeachDTO tutorTeachDTO2 = new TutorTeachDTO();
        assertThat(tutorTeachDTO1).isNotEqualTo(tutorTeachDTO2);
        tutorTeachDTO2.setId(tutorTeachDTO1.getId());
        assertThat(tutorTeachDTO1).isEqualTo(tutorTeachDTO2);
        tutorTeachDTO2.setId(2L);
        assertThat(tutorTeachDTO1).isNotEqualTo(tutorTeachDTO2);
        tutorTeachDTO1.setId(null);
        assertThat(tutorTeachDTO1).isNotEqualTo(tutorTeachDTO2);
    }
}
