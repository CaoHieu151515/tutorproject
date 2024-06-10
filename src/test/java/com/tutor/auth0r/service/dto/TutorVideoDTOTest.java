package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorVideoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorVideoDTO.class);
        TutorVideoDTO tutorVideoDTO1 = new TutorVideoDTO();
        tutorVideoDTO1.setId(1L);
        TutorVideoDTO tutorVideoDTO2 = new TutorVideoDTO();
        assertThat(tutorVideoDTO1).isNotEqualTo(tutorVideoDTO2);
        tutorVideoDTO2.setId(tutorVideoDTO1.getId());
        assertThat(tutorVideoDTO1).isEqualTo(tutorVideoDTO2);
        tutorVideoDTO2.setId(2L);
        assertThat(tutorVideoDTO1).isNotEqualTo(tutorVideoDTO2);
        tutorVideoDTO1.setId(null);
        assertThat(tutorVideoDTO1).isNotEqualTo(tutorVideoDTO2);
    }
}
