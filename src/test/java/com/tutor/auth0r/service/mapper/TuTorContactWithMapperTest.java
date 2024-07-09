package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.TuTorContactWithAsserts.*;
import static com.tutor.auth0r.domain.TuTorContactWithTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TuTorContactWithMapperTest {

    private TuTorContactWithMapper tuTorContactWithMapper;

    @BeforeEach
    void setUp() {
        tuTorContactWithMapper = new TuTorContactWithMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTuTorContactWithSample1();
        var actual = tuTorContactWithMapper.toEntity(tuTorContactWithMapper.toDto(expected));
        assertTuTorContactWithAllPropertiesEquals(expected, actual);
    }
}
