package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.TutorAsserts.*;
import static com.tutor.auth0r.domain.TutorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TutorMapperTest {

    private TutorMapper tutorMapper;

    @BeforeEach
    void setUp() {
        tutorMapper = new TutorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTutorSample1();
        var actual = tutorMapper.toEntity(tutorMapper.toDto(expected));
        assertTutorAllPropertiesEquals(expected, actual);
    }
}
