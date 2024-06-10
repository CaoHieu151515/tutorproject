package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.UserVerifyAsserts.*;
import static com.tutor.auth0r.domain.UserVerifyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserVerifyMapperTest {

    private UserVerifyMapper userVerifyMapper;

    @BeforeEach
    void setUp() {
        userVerifyMapper = new UserVerifyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUserVerifySample1();
        var actual = userVerifyMapper.toEntity(userVerifyMapper.toDto(expected));
        assertUserVerifyAllPropertiesEquals(expected, actual);
    }
}
