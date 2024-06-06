package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.FollowAsserts.*;
import static com.tutor.auth0r.domain.FollowTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FollowMapperTest {

    private FollowMapper followMapper;

    @BeforeEach
    void setUp() {
        followMapper = new FollowMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFollowSample1();
        var actual = followMapper.toEntity(followMapper.toDto(expected));
        assertFollowAllPropertiesEquals(expected, actual);
    }
}
