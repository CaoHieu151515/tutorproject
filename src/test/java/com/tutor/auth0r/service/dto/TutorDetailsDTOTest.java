package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TutorDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TutorDetailsDTO.class);
        TutorDetailsDTO tutorDetailsDTO1 = new TutorDetailsDTO();
        tutorDetailsDTO1.setId(1L);
        TutorDetailsDTO tutorDetailsDTO2 = new TutorDetailsDTO();
        assertThat(tutorDetailsDTO1).isNotEqualTo(tutorDetailsDTO2);
        tutorDetailsDTO2.setId(tutorDetailsDTO1.getId());
        assertThat(tutorDetailsDTO1).isEqualTo(tutorDetailsDTO2);
        tutorDetailsDTO2.setId(2L);
        assertThat(tutorDetailsDTO1).isNotEqualTo(tutorDetailsDTO2);
        tutorDetailsDTO1.setId(null);
        assertThat(tutorDetailsDTO1).isNotEqualTo(tutorDetailsDTO2);
    }
}
