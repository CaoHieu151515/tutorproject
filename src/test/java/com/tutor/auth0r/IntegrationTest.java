package com.tutor.auth0r;

import com.tutor.auth0r.config.AsyncSyncConfiguration;
// import com.tutor.auth0r.config.EmbeddedSQL;
import com.tutor.auth0r.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
// @Target(ElementType.TYPE)
// @Retention(RetentionPolicy.RUNTIME)
// @SpringBootTest(classes = { ProjectApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
// @EmbeddedSQL
public @interface IntegrationTest {
}
