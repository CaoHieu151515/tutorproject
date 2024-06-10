package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.IdentityCardAsserts.*;
import static com.tutor.auth0r.domain.IdentityCardTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdentityCardMapperTest {

    private IdentityCardMapper identityCardMapper;

    @BeforeEach
    void setUp() {
        identityCardMapper = new IdentityCardMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIdentityCardSample1();
        var actual = identityCardMapper.toEntity(identityCardMapper.toDto(expected));
        assertIdentityCardAllPropertiesEquals(expected, actual);
    }
}
