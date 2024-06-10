package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.TutorImageAsserts.*;
import static com.tutor.auth0r.domain.TutorImageTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TutorImageMapperTest {

    private TutorImageMapper tutorImageMapper;

    @BeforeEach
    void setUp() {
        tutorImageMapper = new TutorImageMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTutorImageSample1();
        var actual = tutorImageMapper.toEntity(tutorImageMapper.toDto(expected));
        assertTutorImageAllPropertiesEquals(expected, actual);
    }
}
