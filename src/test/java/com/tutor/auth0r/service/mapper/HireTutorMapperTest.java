package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.HireTutorAsserts.*;
import static com.tutor.auth0r.domain.HireTutorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HireTutorMapperTest {

    private HireTutorMapper hireTutorMapper;

    @BeforeEach
    void setUp() {
        hireTutorMapper = new HireTutorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHireTutorSample1();
        var actual = hireTutorMapper.toEntity(hireTutorMapper.toDto(expected));
        assertHireTutorAllPropertiesEquals(expected, actual);
    }
}
