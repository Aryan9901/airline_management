package com.aryan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables Spring Data JPA auditing for automatic
 * population of entity audit fields such as
 * {@code @CreatedDate} and {@code @LastModifiedDate}.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {

}