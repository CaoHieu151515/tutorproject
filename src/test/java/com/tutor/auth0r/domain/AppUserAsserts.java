package com.tutor.auth0r.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AppUserAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserAllPropertiesEquals(AppUser expected, AppUser actual) {
        assertAppUserAutoGeneratedPropertiesEquals(expected, actual);
        assertAppUserAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserAllUpdatablePropertiesEquals(AppUser expected, AppUser actual) {
        assertAppUserUpdatableFieldsEquals(expected, actual);
        assertAppUserUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserAutoGeneratedPropertiesEquals(AppUser expected, AppUser actual) {
        assertThat(expected)
            .as("Verify AppUser auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserUpdatableFieldsEquals(AppUser expected, AppUser actual) {
        assertThat(expected)
            .as("Verify AppUser relevant properties")
            .satisfies(e -> assertThat(e.getBeTutor()).as("check beTutor").isEqualTo(actual.getBeTutor()))
            .satisfies(e -> assertThat(e.getGender()).as("check gender").isEqualTo(actual.getGender()))
            .satisfies(e -> assertThat(e.getBankAccountNumber()).as("check bankAccountNumber").isEqualTo(actual.getBankAccountNumber()))
            .satisfies(e -> assertThat(e.getBankName()).as("check bankName").isEqualTo(actual.getBankName()))
            .satisfies(e -> assertThat(e.getWalletAddress()).as("check walletAddress").isEqualTo(actual.getWalletAddress()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserUpdatableRelationshipsEquals(AppUser expected, AppUser actual) {
        assertThat(expected)
            .as("Verify AppUser relationships")
            .satisfies(e -> assertThat(e.getTutor()).as("check tutor").isEqualTo(actual.getTutor()))
            .satisfies(e -> assertThat(e.getUserVerify()).as("check userVerify").isEqualTo(actual.getUserVerify()))
            .satisfies(e -> assertThat(e.getRating()).as("check rating").isEqualTo(actual.getRating()));
    }
}