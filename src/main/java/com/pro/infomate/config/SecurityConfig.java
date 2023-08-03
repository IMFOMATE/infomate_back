package com.pro.infomate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/css/**", "/js/**","/images","/lib/**","/productimgs/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        // 개발용 퍼미션
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("*").permitAll();

        // 개발 cors 설정
        http.cors().disable();

//        http.sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
