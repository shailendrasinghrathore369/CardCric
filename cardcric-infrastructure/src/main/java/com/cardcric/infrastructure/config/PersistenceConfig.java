package com.cardcric.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.cardcric.infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "com.cardcric.infrastructure.persistence.spring")
public class PersistenceConfig {
}
