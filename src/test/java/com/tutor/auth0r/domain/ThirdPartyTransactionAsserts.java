package com.tutor.auth0r.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ThirdPartyTransactionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertThirdPartyTransactionAllPropertiesEquals(ThirdPartyTransaction expected, ThirdPartyTransaction actual) {
        assertThirdPartyTransactionAutoGeneratedPropertiesEquals(expected, actual);
        assertThirdPartyTransactionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertThirdPartyTransactionAllUpdatablePropertiesEquals(
        ThirdPartyTransaction expected,
        ThirdPartyTransaction actual
    ) {
        assertThirdPartyTransactionUpdatableFieldsEquals(expected, actual);
        assertThirdPartyTransactionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertThirdPartyTransactionAutoGeneratedPropertiesEquals(
        ThirdPartyTransaction expected,
        ThirdPartyTransaction actual
    ) {
        assertThat(expected)
            .as("Verify ThirdPartyTransaction auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertThirdPartyTransactionUpdatableFieldsEquals(ThirdPartyTransaction expected, ThirdPartyTransaction actual) {
        assertThat(expected)
            .as("Verify ThirdPartyTransaction relevant properties")
            .satisfies(e -> assertThat(e.getThirdPartyId()).as("check thirdPartyId").isEqualTo(actual.getThirdPartyId()))
            .satisfies(e -> assertThat(e.getTransactionDate()).as("check transactionDate").isEqualTo(actual.getTransactionDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertThirdPartyTransactionUpdatableRelationshipsEquals(
        ThirdPartyTransaction expected,
        ThirdPartyTransaction actual
    ) {
        assertThat(expected)
            .as("Verify ThirdPartyTransaction relationships")
            .satisfies(e -> assertThat(e.getWalletTransaction()).as("check walletTransaction").isEqualTo(actual.getWalletTransaction()));
    }
}
