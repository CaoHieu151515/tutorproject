package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicRankDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicRankDTO.class);
        AcademicRankDTO academicRankDTO1 = new AcademicRankDTO();
        academicRankDTO1.setId(1L);
        AcademicRankDTO academicRankDTO2 = new AcademicRankDTO();
        assertThat(academicRankDTO1).isNotEqualTo(academicRankDTO2);
        academicRankDTO2.setId(academicRankDTO1.getId());
        assertThat(academicRankDTO1).isEqualTo(academicRankDTO2);
        academicRankDTO2.setId(2L);
        assertThat(academicRankDTO1).isNotEqualTo(academicRankDTO2);
        academicRankDTO1.setId(null);
        assertThat(academicRankDTO1).isNotEqualTo(academicRankDTO2);
    }
}
