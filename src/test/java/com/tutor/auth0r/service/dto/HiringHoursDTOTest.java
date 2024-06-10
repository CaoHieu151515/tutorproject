package com.tutor.auth0r.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HiringHoursDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HiringHoursDTO.class);
        HiringHoursDTO hiringHoursDTO1 = new HiringHoursDTO();
        hiringHoursDTO1.setId(1L);
        HiringHoursDTO hiringHoursDTO2 = new HiringHoursDTO();
        assertThat(hiringHoursDTO1).isNotEqualTo(hiringHoursDTO2);
        hiringHoursDTO2.setId(hiringHoursDTO1.getId());
        assertThat(hiringHoursDTO1).isEqualTo(hiringHoursDTO2);
        hiringHoursDTO2.setId(2L);
        assertThat(hiringHoursDTO1).isNotEqualTo(hiringHoursDTO2);
        hiringHoursDTO1.setId(null);
        assertThat(hiringHoursDTO1).isNotEqualTo(hiringHoursDTO2);
    }
}
