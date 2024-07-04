package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.HiringHoursTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HiringHoursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HiringHours.class);
        HiringHours hiringHours1 = getHiringHoursSample1();
        HiringHours hiringHours2 = new HiringHours();
        assertThat(hiringHours1).isNotEqualTo(hiringHours2);

        hiringHours2.setId(hiringHours1.getId());
        assertThat(hiringHours1).isEqualTo(hiringHours2);

        hiringHours2 = getHiringHoursSample2();
        assertThat(hiringHours1).isNotEqualTo(hiringHours2);
    }

    @Test
    void tutorTest() throws Exception {
        HiringHours hiringHours = getHiringHoursRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        hiringHours.setTutor(tutorBack);
        assertThat(hiringHours.getTutor()).isEqualTo(tutorBack);

        hiringHours.tutor(null);
        assertThat(hiringHours.getTutor()).isNull();
    }
}
