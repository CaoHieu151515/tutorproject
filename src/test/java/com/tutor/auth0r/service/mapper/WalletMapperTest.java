package com.tutor.auth0r.service.mapper;

import static com.tutor.auth0r.domain.WalletAsserts.*;
import static com.tutor.auth0r.domain.WalletTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WalletMapperTest {

    private WalletMapper walletMapper;

    @BeforeEach
    void setUp() {
        walletMapper = new WalletMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWalletSample1();
        var actual = walletMapper.toEntity(walletMapper.toDto(expected));
        assertWalletAllPropertiesEquals(expected, actual);
    }
}
