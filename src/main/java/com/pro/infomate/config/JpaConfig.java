package com.pro.infomate.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;

@Configuration
@EntityScan(basePackages = "com.pro.infomate")
@EnableJpaRepositories(basePackages = "com.pro.infomate")
@RequiredArgsConstructor
public class JpaConfig {

  private final EntityManager em;
  @Bean
  public JPAQueryFactory queryFactory(){
    return new JPAQueryFactory(em);

  }
}
