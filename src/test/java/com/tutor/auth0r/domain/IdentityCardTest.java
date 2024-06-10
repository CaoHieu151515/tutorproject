package com.tutor.auth0r.domain;

import static com.tutor.auth0r.domain.IdentityCardTestSamples.*;
import static com.tutor.auth0r.domain.MediaTestSamples.*;
import static com.tutor.auth0r.domain.UserVerifyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutor.auth0r.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class IdentityCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentityCard.class);
        IdentityCard identityCard1 = getIdentityCardSample1();
        IdentityCard identityCard2 = new IdentityCard();
        assertThat(identityCard1).isNotEqualTo(identityCard2);

        identityCard2.setId(identityCard1.getId());
        assertThat(identityCard1).isEqualTo(identityCard2);

        identityCard2 = getIdentityCardSample2();
        assertThat(identityCard1).isNotEqualTo(identityCard2);
    }

    @Test
    void hashCodeVerifier() {
        IdentityCard identityCard = new IdentityCard();
        assertThat(identityCard.hashCode()).isZero();

        IdentityCard identityCard1 = getIdentityCardSample1();
        identityCard.setId(identityCard1.getId());
        assertThat(identityCard).hasSameHashCodeAs(identityCard1);
    }

    @Test
    void mediaTest() {
        IdentityCard identityCard = getIdentityCardRandomSampleGenerator();
        Media mediaBack = getMediaRandomSampleGenerator();

        identityCard.addMedia(mediaBack);
        assertThat(identityCard.getMedia()).containsOnly(mediaBack);
        assertThat(mediaBack.getIdentityCard()).isEqualTo(identityCard);

        identityCard.removeMedia(mediaBack);
        assertThat(identityCard.getMedia()).doesNotContain(mediaBack);
        assertThat(mediaBack.getIdentityCard()).isNull();

        identityCard.media(new HashSet<>(Set.of(mediaBack)));
        assertThat(identityCard.getMedia()).containsOnly(mediaBack);
        assertThat(mediaBack.getIdentityCard()).isEqualTo(identityCard);

        identityCard.setMedia(new HashSet<>());
        assertThat(identityCard.getMedia()).doesNotContain(mediaBack);
        assertThat(mediaBack.getIdentityCard()).isNull();
    }

    @Test
    void userVerifyTest() {
        IdentityCard identityCard = getIdentityCardRandomSampleGenerator();
        UserVerify userVerifyBack = getUserVerifyRandomSampleGenerator();

        identityCard.setUserVerify(userVerifyBack);
        assertThat(identityCard.getUserVerify()).isEqualTo(userVerifyBack);
        assertThat(userVerifyBack.getIdentityCard()).isEqualTo(identityCard);

        identityCard.userVerify(null);
        assertThat(identityCard.getUserVerify()).isNull();
        assertThat(userVerifyBack.getIdentityCard()).isNull();
    }
}
