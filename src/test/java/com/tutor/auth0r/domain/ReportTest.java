package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.AppUserTestSamples.*;
import static com.tutor.auth0r.domain.ReportTestSamples.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Report.class);
        Report report1 = getReportSample1();
        Report report2 = new Report();
        assertThat(report1).isNotEqualTo(report2);

        report2.setId(report1.getId());
        assertThat(report1).isEqualTo(report2);

        report2 = getReportSample2();
        assertThat(report1).isNotEqualTo(report2);
    }

    @Test
    void appUserTest() {
        Report report = getReportRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        report.setAppUser(appUserBack);
        assertThat(report.getAppUser()).isEqualTo(appUserBack);

        report.appUser(null);
        assertThat(report.getAppUser()).isNull();
    }

    @Test
    void tutorTest() {
        Report report = getReportRandomSampleGenerator();
        Tutor tutorBack = getTutorRandomSampleGenerator();

        report.setTutor(tutorBack);
        assertThat(report.getTutor()).isEqualTo(tutorBack);

        report.tutor(null);
        assertThat(report.getTutor()).isNull();
    }
}
