package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.HiringHoursAsserts.*;
import static com.tutor.auth0r.domain.HiringHoursTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HiringHoursMapperTest {

    private HiringHoursMapper hiringHoursMapper;

    @BeforeEach
    void setUp() {
        hiringHoursMapper = new HiringHoursMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHiringHoursSample1();
        var actual = hiringHoursMapper.toEntity(hiringHoursMapper.toDto(expected));
        assertHiringHoursAllPropertiesEquals(expected, actual);
    }
}
