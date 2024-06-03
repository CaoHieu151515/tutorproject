package com.tutor.auth0r.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({ "com.tutor.auth0r.repository" })
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {}
