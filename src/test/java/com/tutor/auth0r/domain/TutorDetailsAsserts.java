package com.tutor.auth0r.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TutorDetailsAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorDetailsAllPropertiesEquals(TutorDetails expected, TutorDetails actual) {
        assertTutorDetailsAutoGeneratedPropertiesEquals(expected, actual);
        assertTutorDetailsAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorDetailsAllUpdatablePropertiesEquals(TutorDetails expected, TutorDetails actual) {
        assertTutorDetailsUpdatableFieldsEquals(expected, actual);
        assertTutorDetailsUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorDetailsAutoGeneratedPropertiesEquals(TutorDetails expected, TutorDetails actual) {
        assertThat(expected)
            .as("Verify TutorDetails auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorDetailsUpdatableFieldsEquals(TutorDetails expected, TutorDetails actual) {
        assertThat(expected)
            .as("Verify TutorDetails relevant properties")
            .satisfies(e -> assertThat(e.getContact()).as("check contact").isEqualTo(actual.getContact()))
            .satisfies(e -> assertThat(e.getInformation()).as("check information").isEqualTo(actual.getInformation()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTutorDetailsUpdatableRelationshipsEquals(TutorDetails expected, TutorDetails actual) {
        assertThat(expected)
            .as("Verify TutorDetails relationships")
            .satisfies(e -> assertThat(e.getTutorVideo()).as("check tutorVideo").isEqualTo(actual.getTutorVideo()));
    }
}
