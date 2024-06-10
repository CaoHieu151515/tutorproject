package com.tutor.auth0r.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TutorTeachAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorTeachAllPropertiesEquals(TutorTeach expected, TutorTeach actual) {
        assertTutorTeachAutoGeneratedPropertiesEquals(expected, actual);
        assertTutorTeachAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorTeachAllUpdatablePropertiesEquals(TutorTeach expected, TutorTeach actual) {
        assertTutorTeachUpdatableFieldsEquals(expected, actual);
        assertTutorTeachUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorTeachAutoGeneratedPropertiesEquals(TutorTeach expected, TutorTeach actual) {
        assertThat(expected)
            .as("Verify TutorTeach auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorTeachUpdatableFieldsEquals(TutorTeach expected, TutorTeach actual) {
        assertThat(expected)
            .as("Verify TutorTeach relevant properties")
            .satisfies(e -> assertThat(e.getSubject()).as("check subject").isEqualTo(actual.getSubject()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorTeachUpdatableRelationshipsEquals(TutorTeach expected, TutorTeach actual) {
        assertThat(expected)
            .as("Verify TutorTeach relationships")
            .satisfies(e -> assertThat(e.getTutorDetails()).as("check tutorDetails").isEqualTo(actual.getTutorDetails()));
    }
}