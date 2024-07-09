package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.ReportAsserts.*;
import static com.tutor.auth0r.domain.ReportTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportMapperTest {

    private ReportMapper reportMapper;

    @BeforeEach
    void setUp() {
        reportMapper = new ReportMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getReportSample1();
        var actual = reportMapper.toEntity(reportMapper.toDto(expected));
        assertReportAllPropertiesEquals(expected, actual);
    }
}
