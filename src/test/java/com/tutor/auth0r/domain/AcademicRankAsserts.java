package com.tutor.auth0r.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AcademicRankAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAcademicRankAllPropertiesEquals(AcademicRank expected, AcademicRank actual) {
        assertAcademicRankAutoGeneratedPropertiesEquals(expected, actual);
        assertAcademicRankAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAcademicRankAllUpdatablePropertiesEquals(AcademicRank expected, AcademicRank actual) {
        assertAcademicRankUpdatableFieldsEquals(expected, actual);
        assertAcademicRankUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAcademicRankAutoGeneratedPropertiesEquals(AcademicRank expected, AcademicRank actual) {
        assertThat(expected)
            .as("Verify AcademicRank auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAcademicRankUpdatableFieldsEquals(AcademicRank expected, AcademicRank actual) {
        assertThat(expected)
            .as("Verify AcademicRank relevant properties")
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAcademicRankUpdatableRelationshipsEquals(AcademicRank expected, AcademicRank actual) {
        assertThat(expected)
            .as("Verify AcademicRank relationships")
            .satisfies(e -> assertThat(e.getMedia()).as("check media").isEqualTo(actual.getMedia()))
            .satisfies(e -> assertThat(e.getUserVerify()).as("check userVerify").isEqualTo(actual.getUserVerify()));
    }
}