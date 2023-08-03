package com.pro.infomate.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.pro.infomate")
@EnableJpaRepositories(basePackages = "com.pro.infomate")
public class JpaConfig {
}
