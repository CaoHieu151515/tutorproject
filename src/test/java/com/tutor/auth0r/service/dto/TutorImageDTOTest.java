package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorImageDTO.class);
        TutorImageDTO tutorImageDTO1 = new TutorImageDTO();
        tutorImageDTO1.setId(1L);
        TutorImageDTO tutorImageDTO2 = new TutorImageDTO();
        assertThat(tutorImageDTO1).isNotEqualTo(tutorImageDTO2);
        tutorImageDTO2.setId(tutorImageDTO1.getId());
        assertThat(tutorImageDTO1).isEqualTo(tutorImageDTO2);
        tutorImageDTO2.setId(2L);
        assertThat(tutorImageDTO1).isNotEqualTo(tutorImageDTO2);
        tutorImageDTO1.setId(null);
        assertThat(tutorImageDTO1).isNotEqualTo(tutorImageDTO2);
    }
}
