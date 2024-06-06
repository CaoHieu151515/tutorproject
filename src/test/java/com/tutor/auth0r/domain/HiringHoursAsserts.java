package com.tutor.auth0r.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HiringHoursAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHiringHoursAllPropertiesEquals(HiringHours expected, HiringHours actual) {
        assertHiringHoursAutoGeneratedPropertiesEquals(expected, actual);
        assertHiringHoursAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHiringHoursAllUpdatablePropertiesEquals(HiringHours expected, HiringHours actual) {
        assertHiringHoursUpdatableFieldsEquals(expected, actual);
        assertHiringHoursUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHiringHoursAutoGeneratedPropertiesEquals(HiringHours expected, HiringHours actual) {
        assertThat(expected)
            .as("Verify HiringHours auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHiringHoursUpdatableFieldsEquals(HiringHours expected, HiringHours actual) {
        assertThat(expected)
            .as("Verify HiringHours relevant properties")
            .satisfies(e -> assertThat(e.getHour()).as("check hour").isEqualTo(actual.getHour()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHiringHoursUpdatableRelationshipsEquals(HiringHours expected, HiringHours actual) {
        assertThat(expected)
            .as("Verify HiringHours relationships")
            .satisfies(e -> assertThat(e.getTutor()).as("check tutor").isEqualTo(actual.getTutor()));
    }
}
