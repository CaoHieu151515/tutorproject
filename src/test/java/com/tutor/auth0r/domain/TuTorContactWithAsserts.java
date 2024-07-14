package com.tutor.auth0r.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TuTorContactWithAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTuTorContactWithAllPropertiesEquals(TuTorContactWith expected, TuTorContactWith actual) {
        assertTuTorContactWithAutoGeneratedPropertiesEquals(expected, actual);
        assertTuTorContactWithAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTuTorContactWithAllUpdatablePropertiesEquals(TuTorContactWith expected, TuTorContactWith actual) {
        assertTuTorContactWithUpdatableFieldsEquals(expected, actual);
        assertTuTorContactWithUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTuTorContactWithAutoGeneratedPropertiesEquals(TuTorContactWith expected, TuTorContactWith actual) {
        assertThat(expected)
            .as("Verify TuTorContactWith auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTuTorContactWithUpdatableFieldsEquals(TuTorContactWith expected, TuTorContactWith actual) {
        assertThat(expected)
            .as("Verify TuTorContactWith relevant properties")
            .satisfies(e -> assertThat(e.getUrlContact()).as("check urlContact").isEqualTo(actual.getUrlContact()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTuTorContactWithUpdatableRelationshipsEquals(TuTorContactWith expected, TuTorContactWith actual) {
        assertThat(expected)
            .as("Verify TuTorContactWith relationships")
            .satisfies(e -> assertThat(e.getTutorDetails()).as("check tutorDetails").isEqualTo(actual.getTutorDetails()));
    }
}